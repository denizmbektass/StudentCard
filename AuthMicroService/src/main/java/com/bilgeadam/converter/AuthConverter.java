package com.bilgeadam.converter;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import org.springframework.stereotype.Component;

@Component
public class AuthConverter {

    public static Auth toAuth(RegisterRequestDto dto){
        return Auth.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(ERole.valueOf(dto.getRole()))
                .userId(dto.getUserId())
                .build();
    }
    public static Auth fromLoginDtoToAuth(LoginRequestDto dto){
        return Auth.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

}
