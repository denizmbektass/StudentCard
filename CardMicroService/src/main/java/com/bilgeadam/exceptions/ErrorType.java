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
    CANDIDATE_INTERVIEW_NOT_FOUND(6100, "Aday mülakatı kaydı bulunamadı.", HttpStatus.NOT_FOUND),
    CAREER_EDUCATION_NOT_FOUND(6100, "Kariyer eğitim kaydı bulunamadı.", HttpStatus.NOT_FOUND),
    ASSIGNMENT_NOT_FOUND(6100,"Böyle bir ödev bulunamadı.",HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND(6100,"Böyle bir proje bulunamadı.",HttpStatus.NOT_FOUND),
    INTERNSHIP_NOT_FOUND(6100,"Böyle bir staj bulunamadı.",HttpStatus.NOT_FOUND),
    EXAM_NOT_FOUND(6100,"Böyle bir sınav bulunamadı.",HttpStatus.NOT_FOUND),
    ROLLCALL_NOT_FOUND(6100,"Böyle bir grup bulunamadı.",HttpStatus.BAD_REQUEST),
    CARD_PARAMETER_NOT_FOUND(6100,"Böyle bir parametre bulunamadı.",HttpStatus.BAD_REQUEST),
    APPLICATION_PROCESS_NOT_FOUND(6100, "Böyle bir kayıt bulanamadı.", HttpStatus.NOT_FOUND),

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
    APPLICATION_PROCESS_SCORE_NUMBER_RANGE(9010, "Başvuru süreci puanı, '0' ile '100' arasında olmak zorundadır.", HttpStatus.BAD_REQUEST),
    GROUP_NOT_FOUND(9011,"Grup bulunamadı.",HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND(9012,"Öğrenci bulunamadı.",HttpStatus.BAD_REQUEST),
    STUDENT_ID_NOT_FOUND(9013, "Bu id'ye sahip öğrenci bulunamadı.",HttpStatus.BAD_REQUEST),
    GROUP_ALREADY_EXIST(9014, "Grup zaten kayıtlı.", HttpStatus.BAD_REQUEST),
    STUDENT_APPLICATION_PROCESS_ALREADY_EXIST(9014, "Öğrenciye ait başvuru süreci zaten kayıtlı.", HttpStatus.BAD_REQUEST),

    TRAINER_ASSESSMENT_EMPTY(9015,"Eğitmen görüşü boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    TRAINER_ASSESSMENT_POINT_RANGE(9016, "Eğitmen görüş puanı '0' ile '100' arasında olmalıdır.",HttpStatus.BAD_REQUEST),
    TRAINER_ASSESSMENT_COEFFICIENTS_EMPTY(9016,"Eğitmen görüşü katsayıları boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE(9016, "Eğitmen görüş puan katsayıları '0' ile '1' arasında olmalıdır.",HttpStatus.BAD_REQUEST),
    TOTAL_TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE(9016, "Eğitmen görüş puan katsayıları toplamı '1' olmalıdır.",HttpStatus.BAD_REQUEST),
    TOTAL_TRAINER_ASSESSMENT_POINT_RANGE(9016, "Eğitmen görüş puanları toplamı '0' ile '100' arasında olmalıdır.",HttpStatus.BAD_REQUEST),
    POINT_EMPTY(9016,"Puan boş bırakılamaz.",HttpStatus.BAD_REQUEST),
    INTERVIEW_TYPE_EMPTY(9017, "Mülakat türü boş bırakılamaz.",HttpStatus.BAD_REQUEST),
    INTERVIEW_NAME_EMPTY(9018,"Mülakat adı boş bırakılamaz.",HttpStatus.BAD_REQUEST),
    PROJECT_POINT_EMPTY(9019,"Proje notu boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    PROJECT_TYPE_EMPTY(9020, "Proje tipi boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    DESCRIPTION_EMPTY(9021, "Açıklama boş bırakılamaz.", HttpStatus.BAD_REQUEST),
    GRADUATION_NUMBER_RANGE(9022,"Girilen notlar '0' ile '100' arasında olmak zorundadır.",HttpStatus.BAD_REQUEST),
    BEHAVIOR_NUMBER_RANGE(9023,"Davranış puanı '0' ile '100' arasında olmak zorundadır", HttpStatus.BAD_REQUEST),
    BEHAVIOR_POINT_EMPTY(9024,"Davranış puanı boş bırakılamaz", HttpStatus.BAD_REQUEST),
    BEHAVIOR_NOT_FOUND(9025, "Proje bulunamadı", HttpStatus.BAD_REQUEST),
    TOTAL_PERCENTAGE(9026,"Girilen puanlama yüzdelerinin toplamı tam 100'e eşit olmalıdır",HttpStatus.BAD_REQUEST),
    ALGORITHM_NOT_FOUND(9027, "Algoritma puanı bulunamadı", HttpStatus.BAD_REQUEST),
    TECHNICAL_INTERVIEW_NOT_FOUND(9028, "Teknik mülakat puanı bulunamadı", HttpStatus.BAD_REQUEST),
    WRITTENEXAM_NUMBER_RANGE(9027,"Doğru sayısı '0' ile '35' arasında olmak zorundadır", HttpStatus.BAD_REQUEST),
    WRITTENEXAM_NOT_FOUND(9028,"Yazılı sınav puanı bulunamadı.", HttpStatus.BAD_REQUEST),
    DOCUMENTSUBMIT_NUMBER_OUT_RANGE(9029,"Girilen değer 0 ile 100 arasında olmalıdır", HttpStatus.BAD_REQUEST),
    DOCUMENTSUBMIT_NOT_FOUND(9029,"Evrak teslim puanı bulunamadı", HttpStatus.BAD_REQUEST),
    EMPLOYMENT_INTERVIEW_NUMBER_RANGE(9029,"Mülakat notları  '0' ile '100' arasında olmak zorundadır.",HttpStatus.BAD_REQUEST),
    EMPLOYMENT_INTERVIEW_NOT_FOUND(9030,"Mülakatlar bulunamadı" , HttpStatus.BAD_REQUEST),
    TEAMWORK_NOT_FOUND(9031, "Ekip çalışması bulunamadı." , HttpStatus.BAD_REQUEST),
    ATTENDANCE_NUMBER_RANGE(9031,"Girilen not 0 ile 100 arasında olmalıdıır.",HttpStatus.BAD_REQUEST),
    CONTRIBUTION_NOT_FOUND(9031,"Katkı değerlendirmesi bulunamadı.", HttpStatus.BAD_REQUEST),
    PERSONAL_MOTIVATION_NUMBER_RANGE(9031,"Girilen puan '0' ile '100' arasında olmak zorundadır", HttpStatus.BAD_REQUEST),
    PERSONAL_MOTIVATION_POINT_EMPTY(9032,"Puan boş bırakılamaz", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
