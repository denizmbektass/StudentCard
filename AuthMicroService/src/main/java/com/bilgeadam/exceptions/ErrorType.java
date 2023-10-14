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
    EMAIL_DUPLICATE(4001,"E-posta zaten mevcut.",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(4001,"E-posta bulunamadı. Lütfen tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    PASSWORD_UNMATCH(4002,"Parolalar eşleşmiyor.",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4003,"E-posta veya parola hatalı.",HttpStatus.BAD_REQUEST),
    EXIST_BY_EMAIL(4004,"E-posta zaten mevcut.",HttpStatus.BAD_REQUEST),
    STATUS_NOT_ACTIVE(4005,"Kullanıcı durumu etkin değil, etkinleştirme bağlantısını almak için lütfen e-postanızı kontrol edin.",HttpStatus.BAD_REQUEST),
    USER_DELETED(4006,"Kullanıcı silinmiş, lütfen yönetici ile iletişime geçin.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(5001,"Token oluşturulamadı.",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5100,"Beklenmeyen bir hata oluştu.",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası.",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND (4200,"Kullanıcı bulunamadı.", HttpStatus.BAD_REQUEST),
    SECRET_KEY_NOT_FOUND(4007,"Secret Key bulunamadı.", HttpStatus.BAD_REQUEST),
    CODE_NO_VALID(4008, "Girilen kod geçerli değil.", HttpStatus.BAD_REQUEST)

;


    private int code;
    private String message;
    HttpStatus httpStatus;
}
