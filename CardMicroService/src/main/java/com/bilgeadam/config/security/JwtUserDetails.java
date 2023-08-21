package com.bilgeadam.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetails implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public UserDetails loadUserByRole(List<String> userRoles,String id) throws UsernameNotFoundException {
            List<GrantedAuthority> authorityList = new ArrayList<>();
            userRoles.stream().forEach(item -> {
                 authorityList.add(new SimpleGrantedAuthority(item));
            });

            return User.builder()
                    .username(id)
                    .password("")
                    .accountLocked(false)
                    .accountExpired(false)
                    .authorities(authorityList)
                    .build();
        }
}

