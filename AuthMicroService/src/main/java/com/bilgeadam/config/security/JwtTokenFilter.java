package com.bilgeadam.config.security;

import com.bilgeadam.dto.response.GetIdRoleStatusEmailFromTokenResponseDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.AuthServiceException;
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
            final String authHeader = request.getHeader("Authorization");
        if (authHeader!=null&&authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            GetIdRoleStatusEmailFromTokenResponseDto dto = jwtTokenManager.getIdRoleStatusEmailFromToken(token);
            Optional<String> id = Optional.of(dto.getId());
            List<String> userRole = dto.getRole();
            Optional<EStatus> status = Optional.of(dto.getStatus());
            if (!status.isPresent())
                throw new AuthServiceException(ErrorType.INVALID_TOKEN);
            else if (!status.get().toString().equals("ACTIVE"))
                throw new AuthServiceException(ErrorType.STATUS_NOT_ACTIVE);
            else if (!userRole.isEmpty()) {
                UserDetails userDetails = jwtUserDetails.loadUserByAuthId(id.get());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                throw new AuthServiceException(ErrorType.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request,response);
    }
}
