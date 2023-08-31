package com.bilgeadam.config.security;

import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetails implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public UserDetails loadUserByEmail(List<String> userRoles,String email) throws UsernameNotFoundException {
            List<GrantedAuthority> authorityList = new ArrayList<>();
            userRoles.stream().forEach(item -> {
                 authorityList.add(new SimpleGrantedAuthority(item));
            });

            return org.springframework.security.core.userdetails.User.builder()
                    .username(email)
                    .password("")
                    .accountLocked(false)
                    .accountExpired(false)
                    .authorities(authorityList)
                    .build();
        }
}

