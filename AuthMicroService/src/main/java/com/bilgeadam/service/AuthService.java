package com.bilgeadam.service;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.ResetPasswordRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.MessageResponseDto;
import com.bilgeadam.exceptions.AuthServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.ResetPasswordModel;
import com.bilgeadam.rabbitmq.producer.ResetPasswordProducer;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, String> {
    private final ResetPasswordProducer resetPasswordProducer;
    private final IAuthRepository iAuthRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IAuthMapper iAuthMapper;

    public AuthService(ResetPasswordProducer resetPasswordProducer, IAuthRepository iAuthRepository, JwtTokenManager jwtTokenManager, IAuthMapper iAuthMapper) {
        super(iAuthRepository);
        this.resetPasswordProducer = resetPasswordProducer;
        this.iAuthRepository = iAuthRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.iAuthMapper = iAuthMapper;
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> authOptional = iAuthRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (authOptional.isEmpty())
            throw new AuthServiceException(ErrorType.LOGIN_ERROR);
        if (authOptional.get().getStatus().equals(EStatus.PASSIVE))
            throw new AuthServiceException(ErrorType.STATUS_NOT_ACTIVE);
        if(authOptional.get().getStatus().equals(EStatus.DELETED))
            throw new AuthServiceException(ErrorType.USER_DELETED);
        List<ERole> role = authOptional.get().getRole();
        List<String> roles = role.stream().map(x -> x.name()).toList();
        Optional<String> token = jwtTokenManager.createToken(authOptional.get().getAuthId(), roles, authOptional.get().getStatus(), authOptional.get().getEmail());
        if (token.isEmpty())
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        return LoginResponseDto.builder().token(token.get()).message("Login Successfully").role(roles).build();
    }

    public MessageResponseDto register(RegisterRequestDto dto) {
        Optional<Auth> authOptional = iAuthRepository.findByEmail(dto.getEmail());
        if (authOptional.isPresent())
            throw new AuthServiceException(ErrorType.EXIST_BY_EMAIL);
        String password = CodeGenerator.generateCode();
        Auth auth = Auth.builder().role(dto.getRole().stream().map(x-> ERole.valueOf(x.toUpperCase())).toList()).email(dto.getEmail()).password(password).build();
        save(auth);
        resetPasswordProducer.sendNewPassword(ResetPasswordModel.builder().email(auth.getEmail()).password(auth.getPassword()).build());
        return MessageResponseDto.builder().message("Register has been completed successfully, Password needs to be updated for activating the profile!").build();
    }

    public MessageResponseDto forgotMyPassword(String email) {
        Optional<Auth> auth = iAuthRepository.findByEmail(email);
        if (auth.isEmpty())
            throw new AuthServiceException(ErrorType.EMAIL_NOT_FOUND);
        if(auth.get().getStatus().equals(EStatus.DELETED))
            throw new AuthServiceException(ErrorType.USER_DELETED);
        auth.get().setPassword(CodeGenerator.generateCode());
        auth.get().setStatus(EStatus.PASSIVE);
        update(auth.get());
        resetPasswordProducer.sendNewPassword(ResetPasswordModel.builder().email(auth.get().getEmail()).password(auth.get().getPassword()).build());
        return MessageResponseDto.builder().message("Your password has been sent by e-mail, Password needs to be updated for activating the profile!").build();
    }

    public Boolean resetPassword(ResetPasswordRequestDto dto) {
        Optional<Auth> authOptional = iAuthRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (authOptional.isEmpty()) {
            throw new AuthServiceException(ErrorType.LOGIN_ERROR);
        }
        if(authOptional.get().getStatus().equals(EStatus.DELETED))
            throw new AuthServiceException(ErrorType.USER_DELETED);
        if (dto.getNewPassword().equals(dto.getReNewPassword())) {
            authOptional.get().setPassword(dto.getNewPassword());
            authOptional.get().setStatus(EStatus.ACTIVE);
            save(authOptional.get());
        } else {
            throw new AuthServiceException(ErrorType.PASSWORD_UNMATCH);
        }
        return true;
    }

}
