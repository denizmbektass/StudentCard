package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.ChangePasswordResponseDto;
import com.bilgeadam.dto.response.GetAuthInfoForChangePassword;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.MessageResponseDto;
import com.bilgeadam.exceptions.AuthServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.ActivationLinkMailModel;
import com.bilgeadam.rabbitmq.model.RegisterStudentAndTrainerModel;
import com.bilgeadam.rabbitmq.model.ResetPasswordModel;
import com.bilgeadam.rabbitmq.producer.ActivationLinkProducer;
import com.bilgeadam.rabbitmq.producer.RegisterStudentAndTrainerProducer;
import com.bilgeadam.rabbitmq.producer.ResetPasswordProducer;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, String> {
    private final ResetPasswordProducer resetPasswordProducer;
    private final IAuthRepository iAuthRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IUserManager userManager;
    private final IAuthMapper iAuthMapper;
    private final ActivationLinkProducer activationLinkProducer;
    private final RegisterStudentAndTrainerProducer registerStudentAndTrainerProducer;

    public AuthService(ResetPasswordProducer resetPasswordProducer, IAuthRepository iAuthRepository, JwtTokenManager jwtTokenManager, IUserManager userManager, IAuthMapper iAuthMapper, ActivationLinkProducer activationLinkProducer,RegisterStudentAndTrainerProducer registerStudentAndTrainerProducer) {
        super(iAuthRepository);
        this.resetPasswordProducer = resetPasswordProducer;
        this.iAuthRepository = iAuthRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
        this.iAuthMapper = iAuthMapper;
        this.activationLinkProducer = activationLinkProducer;
        this.registerStudentAndTrainerProducer = registerStudentAndTrainerProducer;
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> authOptional = iAuthRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (authOptional.isEmpty())
            throw new AuthServiceException(ErrorType.LOGIN_ERROR);
        if (authOptional.get().getStatus().equals(EStatus.INACTIVE)) {
            activationLinkProducer.sendActivationLink(ActivationLinkMailModel.builder().authId(authOptional.get().getAuthId()).email(dto.getEmail()).build());
            throw new AuthServiceException(ErrorType.STATUS_NOT_ACTIVE);
        }
        if (authOptional.get().getStatus().equals(EStatus.DELETED))
            throw new AuthServiceException(ErrorType.USER_DELETED);
        /*
        List<ERole> role = authOptional.get().getRole();
        List<String> roles = role.stream().map(x -> x.name()).toList();
        Optional<String> token = jwtTokenManager.createToken(authOptional.get().getAuthId(), roles, authOptional.get().getStatus(), authOptional.get().getEmail(),authOptional.get().getUserId());
        */
        String authStatus = authOptional.get().getStatus().toString();
        String token = jwtTokenManager.createTokenForQr(authOptional.get().getAuthId()).orElseThrow(()->{
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        });
        if (token.isEmpty())
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        return LoginResponseDto.builder().token(token).message("Login Successfully").status(authStatus).build();
    }

    public MessageResponseDto register(RegisterRequestDto dto) {
        Optional<Auth> authOptional = iAuthRepository.findByEmail(dto.getEmail());
        if (authOptional.isPresent())
            throw new AuthServiceException(ErrorType.EXIST_BY_EMAIL);
        String password = CodeGenerator.generateCode();
 /*       Auth auth = Auth.builder()
                .role(dto.getRole().stream().map(x-> ERole.valueOf(x.toUpperCase())).toList())
                .email(dto.getEmail())
                .password(password)
                .build();*/
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto, password);
        auth.setStatus(EStatus.INACTIVE);
        auth.setRole(List.of(ERole.ADMIN));
        String userId = userManager.registerManagerForUser(dto).getBody();
        auth.setUserId(userId);
        save(auth);
        resetPasswordProducer.sendNewPassword(ResetPasswordModel.builder().email(auth.getEmail()).password(auth.getPassword()).build());
        return MessageResponseDto.builder().message("Register has been completed successfully, Password needs to be updated for activating the profile!").build();
    }

    public MessageResponseDto forgotMyPassword(String email) {
        Optional<Auth> auth = iAuthRepository.findByEmail(email);
        if (auth.isEmpty())
            throw new AuthServiceException(ErrorType.EMAIL_NOT_FOUND);
        if (auth.get().getStatus().equals(EStatus.DELETED))
            throw new AuthServiceException(ErrorType.USER_DELETED);
        auth.get().setPassword(CodeGenerator.generateCode());
        auth.get().setStatus(EStatus.PASSIVE);
        update(auth.get());
        resetPasswordProducer.sendNewPassword(ResetPasswordModel.builder().email(auth.get().getEmail()).password(auth.get().getPassword()).build());
        return MessageResponseDto.builder().message("Your password has been sent by e-mail, Password needs to be updated for activating the profile!").build();
    }

    public Boolean resetPassword(ResetPasswordRequestDto dto) {
        Optional<String> authIdOptional = jwtTokenManager.getIdFromToken(dto.getToken());
        Optional<Auth> authOptional = iAuthRepository.findById(authIdOptional.get());
        if (authOptional.isEmpty()) {
            throw new AuthServiceException(ErrorType.LOGIN_ERROR);
        }
        if (authOptional.get().getStatus().equals(EStatus.DELETED))
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

    public Boolean activateUser(String token) {
        String authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        });
        Optional<Auth> optionalAuth = findById(authId);
        optionalAuth.get().setStatus(EStatus.ACTIVE);
        update(optionalAuth.get());
        return true;
    }

    /**
     * Bu method user service'de change password metodu için yazıldı. O User'ın bilgilerini çekmek için kullanılmaktadır.
     * @param userId
     * @return
     */
    public GetAuthInfoForChangePassword getAuthInfoForChangePassword(String userId) {
        Optional<Auth> optionalAuth= iAuthRepository.findByUserId(userId);
        if (optionalAuth.isEmpty()) throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        return  GetAuthInfoForChangePassword.builder().password(optionalAuth.get().getPassword()).userId(userId).build();
    }

    public Boolean changePasswordForAuth (ChangePasswordResponseDto dto){
        Optional<Auth> optionalAuth = iAuthRepository.findByUserId(dto.getUserId());
        if (optionalAuth.isEmpty())
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        optionalAuth.get().setPassword(dto.getNewPassword());
        update(optionalAuth.get());
        return true;
    }
    public List<ERole> getRoleFromToken(String token){
        Optional<String> authId= jwtTokenManager.getIdFromToken(token);
        if (authId.isEmpty()) throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        Optional<Auth> optionalAuth=iAuthRepository.findById(authId.get());
        if (optionalAuth.isEmpty()) throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        return optionalAuth.get().getRole();
    }
    public MessageResponseDto registerStudentAndTrainer(RegisterStudentAndTrainerRequestDto dto) {
        Optional<Auth> authOptional = iAuthRepository.findByEmail(dto.getEmail());
        if (authOptional.isPresent())
            throw new AuthServiceException(ErrorType.EXIST_BY_EMAIL);
        String password = CodeGenerator.generateCode();
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto, password);
        auth.setStatus(EStatus.INACTIVE);
        save(auth);
        registerStudentAndTrainerProducer.registerStudentAndTrainer(RegisterStudentAndTrainerModel.builder()
                .email(auth.getEmail())
                .password(auth.getPassword())
                .build());
        return MessageResponseDto.builder().message("Register has been completed successfully, Password needs to be updated for activating the profile!").build();
    }
}
