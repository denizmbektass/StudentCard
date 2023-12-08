package com.bilgeadam.config.security;

import com.bilgeadam.repository.enums.ERole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetails implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public UserDetails loadUserByEmail(List<String> userRoles, String email) throws UsernameNotFoundException {
        List<ERole> roles = userRoles.stream()
                .map(ERole::valueOf)
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = roles.stream()
                .flatMap(role -> role.getAuthorities().stream())
                .collect(Collectors.toList());

        return User.builder()
                .username(email)
                .password("")
                .accountLocked(false)
                .accountExpired(false)
                .authorities(authorities)
                .build();
    }
}

