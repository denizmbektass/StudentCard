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

    INTERVIEW_NOT_FOUND(6100,"Böyle bir mülakat bulunamadı",HttpStatus.BAD_REQUEST),
    ASSIGNMENT_NOT_FOUND(6100,"Böyle bir ödev bulunamadı",HttpStatus.BAD_REQUEST),
    EXAM_NOT_FOUND(6100,"Böyle bir sınav bulunamadı",HttpStatus.BAD_REQUEST),

    TRAINER_ASSESSMENT_NOT_FOUND(6001,"Trainer Assessment is not found please try again",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(500,"User not exist",HttpStatus.BAD_REQUEST),
    ABSENCE_NOT_FOUND(7001,"Absence is not found please try again",HttpStatus.NOT_FOUND),

    USER_NOT_FOUND(8001,"Kullanıcı bulunamadı",HttpStatus.NOT_FOUND),
    POINT_SUCCESS_RATE(8002,"Girdiğiniz puan 0 ile 100 arasında olmalıdır.",HttpStatus.BAD_REQUEST),
    COMMENT_LENGTH_VERGE(8003, "Girilen yorum 255 karakterden fazla olamaz",HttpStatus.BAD_REQUEST),

    ;


    private int code;
    private String message;
     HttpStatus httpStatus;
}
