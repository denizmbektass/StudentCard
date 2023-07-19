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
    EMAIL_NOT_FOUND(4001,"E-mail is not found please try again",HttpStatus.BAD_REQUEST),
    PASSWORD_UNMATCH(4002,"Passwords are not matched",HttpStatus.BAD_REQUEST),

    LOGIN_ERROR(4003,"Login error",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(5001,"Token not created",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5100,"Eternal Error",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parameter Error",HttpStatus.BAD_REQUEST),

    INTERVIEW_NOT_FOUND(6100,"Böyle bir mülakat bulunamadı",HttpStatus.BAD_REQUEST)





    ;


    private int code;
    private String message;
     HttpStatus httpStatus;
}
