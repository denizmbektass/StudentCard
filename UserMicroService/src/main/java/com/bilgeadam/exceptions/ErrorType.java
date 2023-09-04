package com.bilgeadam.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    TOKEN_NOT_CREATED(3001,"Token not created",HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATE(4001,"E-mail is already exist",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(4002,"E-mail is not found please try again",HttpStatus.BAD_REQUEST),
    PASSWORD_UNMATCH(4003,"Passwords are not matched",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4004,"Login error",HttpStatus.BAD_REQUEST),
    STATUS_NOT_ACTIVE(4005,"User status is not active, please reset your password first to make it active",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(5001,"Token error",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5100,"Eternal Error",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parameter Error",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(500,"User not exist",HttpStatus.BAD_REQUEST),
    USER_WRONG_PASSWORD(4006,"Your last password is not correct",HttpStatus.BAD_REQUEST),




    ;


    private int code;
    private String message;
     HttpStatus httpStatus;
}
