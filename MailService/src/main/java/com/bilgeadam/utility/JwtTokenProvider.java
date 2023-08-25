package com.bilgeadam.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {
    private final String issuer = "StudentCard";
    private final String secretKey = "StudentCard";
    private final String audience = "audience";

    public Optional<String> createTokenForActivationLink(String id){
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000*60*60*24));
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
