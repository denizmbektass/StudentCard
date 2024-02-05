package com.bilgeadam.utility;

import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class DatabaseSeeder {
    @Value("${SCADMIN_PWD:1234}")
    private String SCADMIN_PWD;
    @Value("${EMPLOYMENT_PWD:1234}")
    private String EMPLOYMENT_PWD;
    @Value("${WORKSHOP_PWD:1234}")
    private String WORKSHOP_PWD;
    @Value("${INTERNSHIP_PWD:1234}")
    private String INTERNSHIP_PWD;
    @Value("${EDUCATION_PWD:1234}")
    private String EDUCATION_PWD;

    @Bean
    public ApplicationRunner runner(IAuthRepository authRepository){
        return args -> {
            if(authRepository.findByEmail("scadmin@bilgeadam.com").isEmpty()){
                String id1 = UUID.randomUUID().toString();
                Auth a = Auth
                        .builder()
                        .name("Admin")
                        .surname("Admin")
                        .authId(id1)
                        .userId(id1)
                        .role(List.of(ERole.ADMIN))
                        .status(EStatus.ACTIVE)
                        .email("scadmin@bilgeadam.com")
                        .password(SCADMIN_PWD)
                        .build();

                authRepository.save(a);
            }
            if(authRepository.findByEmail("serli.cakir@bilgeadam.com").isEmpty()){
                String id1 = UUID.randomUUID().toString();
                Auth a = Auth
                        .builder()
                        .name("Serli")
                        .surname("Çakır")
                        .authId(id1)
                        .userId(id1)
                        .role(List.of(ERole.EMPLOYMENT_TEAM))
                        .status(EStatus.ACTIVE)
                        .email("serli.cakir@bilgeadam.com")
                        .password(EMPLOYMENT_PWD)
                        .build();

                authRepository.save(a);
            }
            if(authRepository.findByEmail("busra.armagan@bilgeadam.com").isEmpty()){
                String id1 = UUID.randomUUID().toString();
                Auth a = Auth
                        .builder()
                        .name("Büşra")
                        .surname("Armağan")
                        .authId(id1)
                        .userId(id1)
                        .role(List.of(ERole.MASTER_TRAINER))
                        .status(EStatus.ACTIVE)
                        .email("busra.armagan@bilgeadam.com")
                        .password(EDUCATION_PWD)
                        .build();

                authRepository.save(a);
            }
            if(authRepository.findByEmail("volkan.kavlan@bilgeadam.com").isEmpty()){
                String id1 = UUID.randomUUID().toString();
                Auth a = Auth
                        .builder()
                        .name("Volkan")
                        .surname("Kavlan")
                        .authId(id1)
                        .userId(id1)
                        .role(List.of(ERole.INTERNSHIP_TEAM))
                        .status(EStatus.ACTIVE)
                        .email("volkan.kavlan@bilgeadam.com")
                        .password(INTERNSHIP_PWD)
                        .build();

                authRepository.save(a);
            }
            if(authRepository.findByEmail("ezgi.cinar@bilgeadam.com").isEmpty()){
                String id1 = UUID.randomUUID().toString();
                Auth a = Auth
                        .builder()
                        .name("Ezgi")
                        .surname("Çınar")
                        .authId(id1)
                        .userId(id1)
                        .role(List.of(ERole.WORKSHOP_TEAM))
                        .status(EStatus.ACTIVE)
                        .email("ezgi.cinar@bilgeadam.com")
                        .password(WORKSHOP_PWD)
                        .build();

                authRepository.save(a);
            }
        };
    }
}
