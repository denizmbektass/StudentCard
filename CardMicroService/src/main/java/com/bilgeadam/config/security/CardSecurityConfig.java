package com.bilgeadam.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.*;
import static com.bilgeadam.repository.enums.ERole.*;
import static com.bilgeadam.repository.enums.EPermission.*;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CardSecurityConfig {

    @Bean
    JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui/**","/v3/api-docs/**","/api/v1/card/**").permitAll()
                .antMatchers("/api/v1/card/**").hasAnyRole(ADMIN.name(), MASTER_TRAINER.name(), ASSISTANT_TRAINER.name(), STUDENT.name(), WORKSHOP_TEAM.name(), INTERNSHIP_TEAM.name(), EMPLOYMENT_TEAM.name())
                .antMatchers(GET, "/api/v1/card/education/**").hasAnyAuthority(ADMIN_READ.name(), MASTER_TRAINER_READ.name(), ASSISTANT_TRAINER_READ.name(), WORKSHOP_TEAM_READ.name(), INTERNSHIP_TEAM_READ.name(), EMPLOYMENT_TEAM_READ.name())
                .antMatchers(POST, "/api/v1/card/education/**").hasAnyAuthority(ADMIN_WRITE.name(), MASTER_TRAINER_WRITE.name(), ASSISTANT_TRAINER_WRITE.name())
                .antMatchers(PUT, "/api/v1/card/education/**").hasAnyAuthority(ADMIN_WRITE.name(), MASTER_TRAINER_WRITE.name(), ASSISTANT_TRAINER_WRITE.name())
                .antMatchers(DELETE, "/api/v1/card/education/**").hasAnyAuthority(ADMIN_WRITE.name(), MASTER_TRAINER_WRITE.name(), ASSISTANT_TRAINER_WRITE.name())
                .antMatchers(GET, "/api/v1/card/student-choice/**").hasAnyAuthority(ADMIN_READ.name(), MASTER_TRAINER_READ.name(), ASSISTANT_TRAINER_READ.name(), WORKSHOP_TEAM_READ.name(), INTERNSHIP_TEAM_READ.name(), EMPLOYMENT_TEAM_READ.name())
                .antMatchers(POST, "/api/v1/card/student-choice/**").hasAnyAuthority(ADMIN_WRITE.name(), WORKSHOP_TEAM_WRITE.name())
                .antMatchers(PUT, "/api/v1/card/student-choice/**").hasAnyAuthority(ADMIN_WRITE.name(), WORKSHOP_TEAM_WRITE.name())
                .antMatchers(DELETE, "/api/v1/card/student-choice/**").hasAnyAuthority(ADMIN_WRITE.name(), WORKSHOP_TEAM_WRITE.name())
                .antMatchers(GET, "/api/v1/card/employment/**").hasAnyAuthority(ADMIN_READ.name(), MASTER_TRAINER_READ.name(), ASSISTANT_TRAINER_READ.name(), WORKSHOP_TEAM_READ.name(), INTERNSHIP_TEAM_READ.name(), EMPLOYMENT_TEAM_READ.name())
                .antMatchers(POST, "/api/v1/card/employment/**").hasAnyAuthority(ADMIN_WRITE.name(), EMPLOYMENT_TEAM_WRITE.name())
                .antMatchers(PUT, "/api/v1/card/employment/**").hasAnyAuthority(ADMIN_WRITE.name(), EMPLOYMENT_TEAM_WRITE.name())
                .antMatchers(DELETE, "/api/v1/card/employment/**").hasAnyAuthority(ADMIN_WRITE.name(), EMPLOYMENT_TEAM_WRITE.name())
                .antMatchers(GET, "/api/v1/card/internship-success/**").hasAnyAuthority(ADMIN_READ.name(), MASTER_TRAINER_READ.name(), ASSISTANT_TRAINER_READ.name(), WORKSHOP_TEAM_READ.name(), INTERNSHIP_TEAM_READ.name(), EMPLOYMENT_TEAM_READ.name())
                .antMatchers(POST, "/api/v1/card/internship-success/**").hasAnyAuthority(ADMIN_WRITE.name(), INTERNSHIP_TEAM_WRITE.name())
                .antMatchers(PUT, "/api/v1/card/internship-success/**").hasAnyAuthority(ADMIN_WRITE.name(), INTERNSHIP_TEAM_WRITE.name())
                .antMatchers(DELETE, "/api/v1/card/internship-success/**").hasAnyAuthority(ADMIN_WRITE.name(), INTERNSHIP_TEAM_WRITE.name())
                .and()
                .cors().and().csrf().disable();
        http .addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
