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
    EMAIL_NOT_FOUND(4001,"E-posta bulunamadı. Lütfen tekrar deneyin.",HttpStatus.BAD_REQUEST),
    PASSWORD_UNMATCH(4002,"Parolalar eşleşmiyor.",HttpStatus.BAD_REQUEST),
    STATUS_NOT_ACTIVE(4003,"Kullanıcı durumu etkin değil, lütfen kullanıcıyı etkinleştirmek için önce parolanızı sıfırlayın.",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4003,"Giriş hatası.",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(5001,"Geçersiz token",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5100,"Beklenmeyen bir hata oluştu.",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası.",HttpStatus.BAD_REQUEST),

    INTERVIEW_NOT_FOUND(6100,"Böyle bir mülakat bulunamadı.",HttpStatus.NOT_FOUND),
    ASSIGNMENT_NOT_FOUND(6100,"Böyle bir ödev bulunamadı.",HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND(6100,"Böyle bir proje bulunamadı.",HttpStatus.NOT_FOUND),
    INTERNSHIP_NOT_FOUND(6100,"Böyle bir staj bulunamadı.",HttpStatus.NOT_FOUND),
    EXAM_NOT_FOUND(6100,"Böyle bir sınav bulunamadı.",HttpStatus.NOT_FOUND),
    ROLLCALL_NOT_FOUND(6100,"Böyle bir grup bulunamadı.",HttpStatus.BAD_REQUEST),
    CARD_PARAMETER_NOT_FOUND(6100,"Böyle bir parametre bulunamadı.",HttpStatus.BAD_REQUEST),

    TRAINER_ASSESSMENT_NOT_FOUND(6001,"Eğitmen değerlendirmesi bulunamadı, lütfen tekrar deneyin.",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(500,"Kullanıcı mevcut.",HttpStatus.BAD_REQUEST),
    ROLE_MISMATCH(500,"Role eşleştirilemedi.",HttpStatus.BAD_REQUEST),
    ABSENCE_NOT_FOUND(7001,"Devamsızlık bulunamadı.",HttpStatus.NOT_FOUND),

    USER_NOT_FOUND(8001,"Kullanıcı bulunamadı.",HttpStatus.NOT_FOUND),
    POINT_SUCCESS_RATE(8002,"Girdiğiniz puan '0' ile '100' arasında olmalıdır.",HttpStatus.BAD_REQUEST),
    COMMENT_LENGTH_VERGE(8003, "Girilen yorum '255' karakterden fazla olamaz.",HttpStatus.BAD_REQUEST),

    PROJECT_TYPE_DUBLICATE(9001,"Böyle bir proje türü zaten bulunmaktadır.",HttpStatus.BAD_REQUEST),
    PROJECT_TYPE_NOT_FOUND(9002,"Böyle bir proje türü bulunamadı.",HttpStatus.BAD_REQUEST),
    PROJECT_TYPE_STATUS(9003,"Proje türü aktif değil.",HttpStatus.BAD_REQUEST),
    TRANSCRIPT_NOT_FOUND(9004,"Transkript bulunamadı.",HttpStatus.BAD_REQUEST),
    HW_NUMBER_RANGE(9005, "Ödev notu, '0' ile '100' arasında olmak zorundadır..",HttpStatus.BAD_REQUEST),
    EXAM_NUMBER_RANGE(9010,"Sınav notu, '0' ile '100' arasında olmak zorundadır.",HttpStatus.BAD_REQUEST),
    INTERVIEW_SCORE_NUMBER_RANGE(9010,"Mülakat puanı, '0' ile '100' arasında olmak zorundadır.",HttpStatus.BAD_REQUEST),
    GROUP_NOT_FOUND(9011,"Grup bulunamadı.",HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND(9012,"Öğrenci bulunamadı.",HttpStatus.BAD_REQUEST),
    STUDENT_ID_NOT_FOUND(9013, "Bu id'ye sahip öğrenci bulunamadı.",HttpStatus.BAD_REQUEST),
    GROUP_ALREADY_EXIST(9014, "Grup zaten kayıtlı.", HttpStatus.BAD_REQUEST),

    TRAINER_ASSESSMENT_EMPTY(9015,"Eğitmen görüşü boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    TRAINER_ASSESSMENT_POINT_RANGE(8003, "Eğitmen görüş puanı '0' ile '100' arasında olmalıdır.",HttpStatus.BAD_REQUEST),
    POINT_EMPTY(9016,"Puan boş bırakılamaz.",HttpStatus.BAD_REQUEST),
    INTERVIEW_TYPE_EMPTY(9017, "Mülakat türü boş bırakılamaz.",HttpStatus.BAD_REQUEST),
    INTERVIEW_NAME_EMPTY(9018,"Mülakat adı boş bırakılamaz.",HttpStatus.BAD_REQUEST),
    PROJECT_POINT_EMPTY(9019,"Proje notu boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    PROJECT_TYPE_EMPTY(9020, "Proje tipi boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    DESCRIPTION_EMPTY(9021, "Açıklama boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    GRADUATION_NUMBER_RANGE(9022,"Girilen notlar '0' ile '100' arasında olmak zorundadır.",HttpStatus.BAD_REQUEST)


    ;


    private int code;
    private String message;
     HttpStatus httpStatus;
}
