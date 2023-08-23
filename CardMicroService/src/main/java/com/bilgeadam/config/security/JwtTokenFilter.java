package com.bilgeadam.config.security;

import com.bilgeadam.dto.response.GetIdRoleStatusEmailFromTokenResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUserDetails jwtUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final String userHeader = request.getHeader("Authorization");
        if (userHeader!=null&&userHeader.startsWith("Bearer ")) {
            String token = userHeader.substring(7);
            GetIdRoleStatusEmailFromTokenResponseDto dto = jwtTokenManager.getIdRoleStatusEmailFromToken(token);
            List<String> userRole = dto.getRole();
            Optional<EStatus> status = Optional.of(dto.getStatus());
            Optional<String> email = Optional.of(dto.getEmail());
            if (!status.isPresent())
                throw new CardServiceException(ErrorType.INVALID_TOKEN);
            else if (!status.get().toString().equals("ACTIVE"))
                throw new CardServiceException(ErrorType.STATUS_NOT_ACTIVE);
            else if (!userRole.isEmpty()) {
                UserDetails userDetails = jwtUserDetails.loadUserByEmail(userRole,email.get());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                throw new CardServiceException(ErrorType.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request,response);
    }
}
