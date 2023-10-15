package com.bilgeadam.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    TOKEN_NOT_CREATED(3001,"Token oluşturulamadı.",HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATE(4001,"E-posta zaten kullanımda.",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(4002,"E-posta bulunamadı. Lütfen tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    PASSWORD_UNMATCH(4003,"Parolalar eşleşmiyor.",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4004,"Giriş hatası.",HttpStatus.BAD_REQUEST),
    STATUS_NOT_ACTIVE(4005,"Kullanıcı durumu etkin değil, etkinleştirme bağlantısını almak için lütfen e-postanızı kontrol edin.",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(5001,"Token hatası.",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5100,"Bilinmeyen bir hata oluştu.",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası.",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(500,"Kullanıcı mevcut değil.",HttpStatus.BAD_REQUEST),
    USER_WRONG_PASSWORD(4006,"Son şifreniz yanlış.",HttpStatus.BAD_REQUEST),
    GROUP_NOT_FOUND(9011,"Grup bulunamadı.",HttpStatus.BAD_REQUEST)




    ;


    private int code;
    private String message;
     HttpStatus httpStatus;
}
