package com.bilgeadam.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.bilgeadam.dto.response.GetIdRoleStatusEmailFromTokenResponseDto;

import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.StudentServiceException;
import com.bilgeadam.repository.enums.EStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenManager {
    private final String issuer = "StudentCard";
    private final String secretKey = "StudentCard";
    private final String audience = "audience";
    public Optional<String> createToken(String id, List<String> role, EStatus status,List<String> groupName, String email){
        String token= null;
        Long exDate = 1000L*60*150;
        try{

            token = JWT.create().withAudience(audience)
                    .withClaim("id",id)
                    .withClaim("groupname",groupName)
                    .withClaim("howtopage","AuthMicroService")
                    .withClaim("lastjoin", System.currentTimeMillis())
                    .withClaim("role",role)
                    .withClaim("status", String.valueOf(status))
                    .withClaim("email",email)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+exDate))
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }
    }

//    public Boolean verifyToken(String token){
//        try{
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer(issuer).withAudience(audience).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//            if(decodedJWT == null)
//                return false;
//        }catch (Exception exception){
//            return false;
//        }
//        return true;
//    }

    public Optional<String> getIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT == null)
                return Optional.empty();
            String id = decodedJWT.getClaim("id").asString();
//            String howToPage = decodedJWT.getClaim("howtopage").asString();
//            System.out.println("howtopage....: "+ howToPage);
            return Optional.of(id);
        }catch (Exception exception){
            return Optional.empty();
        }
    }

//    public List<String> getRoleFromToken(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//            if (decodedJWT == null) {
//                throw new StudentServiceException(ErrorType.INVALID_TOKEN);
//            }
//            List<String> role = decodedJWT.getClaim("role").asList(String.class);
//            return role;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new StudentServiceException(ErrorType.INVALID_TOKEN);
//
//        }
//    }
//    public EStatus getStatusFromToken(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//            if (decodedJWT == null) {
//                throw new StudentServiceException(ErrorType.INVALID_TOKEN);
//            }
//            EStatus status = decodedJWT.getClaim("status").as(EStatus.class);   //DANIŞ
//            return status;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new StudentServiceException(ErrorType.INVALID_TOKEN);
//
//        }
//    }
    public GetIdRoleStatusEmailFromTokenResponseDto getIdRoleStatusEmailFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new StudentServiceException(ErrorType.INVALID_TOKEN);
            }
            EStatus status = decodedJWT.getClaim("status").as(EStatus.class);
            List<String> role = decodedJWT.getClaim("role").asList(String.class);
            String id = decodedJWT.getClaim("id").asString();
            String email = decodedJWT.getClaim("email").asString();
            GetIdRoleStatusEmailFromTokenResponseDto getIdRoleStatusEmailFromTokenResponseDto = GetIdRoleStatusEmailFromTokenResponseDto.builder()
                    .status(status)
                    .email(email)
                    .id(id)
                    .role(role)
                    .build();
            return getIdRoleStatusEmailFromTokenResponseDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        }
    }


    public Optional<String> getIdFromTokenForStudentId(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT == null)
                return Optional.empty();
            String id = decodedJWT.getClaim("studentId").asString();
            return Optional.of(id);
        }catch (Exception exception){
            return Optional.empty();
        }
    }
}
