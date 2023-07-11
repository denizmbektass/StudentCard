package com.bilgeadam.service;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.exceptions.AuthServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.converter.AuthConverter;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, String> {
    private final IAuthRepository iAuthRepository;
    private final AuthConverter authConverter;

    public AuthService(IAuthRepository iAuthRepository, AuthConverter authConverter) {
        super(iAuthRepository);
        this.iAuthRepository = iAuthRepository;
        this.authConverter = authConverter;
    }

    public String login(LoginRequestDto dto) {
        Auth auth = authConverter.fromLoginDtoToAuth(dto);
        save(auth);
        return auth.getId();
    }

    public String register(RegisterRequestDto dto) {
        Auth auth = authConverter.toAuth(dto);
        save(auth);
        return auth.getId();
    }

    public String forgotMyPassword(String email) {
        Optional<Auth> auth = iAuthRepository.findOptionalByEmail(email);
        if (!auth.isPresent())
            throw new AuthServiceException(ErrorType.EMAIL_NOT_FOUND);
        auth.get().setPassword("123456");//TODO: MAIL SERVICE KURULUNCA METOT REVIZE EDILCEK
        update(auth.get());
        return "Password 123456.";
    }
}
