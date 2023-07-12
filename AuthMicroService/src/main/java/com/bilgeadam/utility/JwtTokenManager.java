package com.bilgeadam.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bilgeadam.exceptions.AuthServiceException;
import com.bilgeadam.exceptions.ErrorType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenManager {
    private final String issuer = "StudentCard";
    private final String secretKey = "StudentCard";
    private final String audience = "audience";
    public Optional<String> createToken(String id, List<String> role){
        String token= null;
        Long exDate = 1000L*60*150;
        try{

            token = JWT.create().withAudience(audience)
                    .withClaim("id",id)
                    .withClaim("howtopage","AuthMicroService")
                    .withClaim("lastjoin", System.currentTimeMillis())
                    .withClaim("role",role)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+exDate))
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }
    }

    public Boolean verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT == null)
                return false;
        }catch (Exception exception){
            return false;
        }
        return true;
    }

    public Optional<Long> getByIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT == null)
                return Optional.empty();
            Long id = decodedJWT.getClaim("id").asLong();
//            String howToPage = decodedJWT.getClaim("howtopage").asString();
//            System.out.println("howtopage....: "+ howToPage);
            return Optional.of(id);
        }catch (Exception exception){
            return Optional.empty();
        }
    }

    public List<String> getRoleFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new AuthServiceException(ErrorType.INVALID_TOKEN);
            }
            List<String> role = decodedJWT.getClaim("role").asList(String.class);
            return role;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);

        }
    }
}
