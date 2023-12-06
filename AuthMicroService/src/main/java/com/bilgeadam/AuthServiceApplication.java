package com.bilgeadam;

import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class);
    }

    @Bean
    public ApplicationRunner runner(IAuthRepository authRepository){
        return args -> {
            if(authRepository.findByEmail("scadmin@bilgeadam.com").isEmpty()){
                String id = UUID.randomUUID().toString();
                Auth a = Auth
                        .builder()
                        .name("Admin")
                        .surname("Admin")
                        .authId(id)
                        .userId(id)
                        .role(List.of(ERole.ADMIN))
                        .status(EStatus.ACTIVE)
                        .email("scadmin@bilgeadam.com")
                        .password("1234")
                        .build();

                authRepository.save(a);
            }
        };
    }
}