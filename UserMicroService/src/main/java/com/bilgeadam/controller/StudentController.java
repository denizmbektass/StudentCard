package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.UserServiceException;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.service.StudentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(STUDENT)
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudentController {

    private final StudentService studentService;


    @Operation(summary = "Öğrenci güncelleme işlemi",
            description = "Belirtilen öğrenci bilgilerini günceller.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateStudent(@RequestBody @Valid StudentUpdateRequestDto dto) {
        return ResponseEntity.ok(studentService.updateStudent(dto));
    }

    @Operation(summary = "Öğrenciyi pasif duruma getirme işlemi",
            description = "Belirtilen öğrenciyi pasif duruma getirir.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(DO_PASSIVE)
    public ResponseEntity<Boolean> doPassive(@RequestParam String id) {
        return ResponseEntity.ok(studentService.doPassive(id));
    }

    @Operation(summary = "Güvenli öğrenci silme işlemi",
            description = "Belirtilen öğrenciyi güvenli bir şekilde siler.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(SAFE_DELETE)
    public ResponseEntity<Boolean> safeDelete(@RequestParam String id) {
        return ResponseEntity.ok(studentService.safeDelete(id));
    }

    @Operation(summary = "Öğrenci kaydetme işlemi",
            description = "Belirtilen öğrenci bilgilerini kaydeder.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(SAVE)
    public ResponseEntity<SaveStudentResponseDto> save(@RequestBody SaveStudentRequestDto dto) {
        return ResponseEntity.ok(studentService.save(dto));
    }


    @Operation(summary = "E-posta hatırlatıcı için eğitmenleri getirme işlemi",
            description = "E-posta hatırlatıcı için eğitmenlerin listesini getirir.")
    @GetMapping("/mail-reminder-get-trainers")
    public ResponseEntity<List<TrainersMailReminderDto>> getTrainers() {
        return ResponseEntity.ok(studentService.getTrainers());
    }


    @Operation(summary = "E-posta hatırlatıcı için yöneticileri getirme işlemi",
            description = "E-posta hatırlatıcı için yöneticilerin listesini getirir.")
    @GetMapping("/mail-reminder-get-masters")
    public ResponseEntity<List<MastersMailReminderDto>> getMasters() {
        return ResponseEntity.ok(studentService.getMasters());
    }


    @Operation(summary = "E-posta hatırlatıcı için öğrencileri getirme işlemi",
            description = "E-posta hatırlatıcı için öğrencilerin listesini getirir.")
    @GetMapping("/mail-reminder-get-students")
    public ResponseEntity<List<StudentsMailReminderDto>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @Operation(summary = "Öğrenci arama işlemi",
            description = "Belirli kriterlere göre öğrencileri aramak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/search-student")
    public ResponseEntity<List<Student>> searchStudent(@RequestBody SearchStudentRequestDto dto) {
        return ResponseEntity.ok(studentService.searchStudent(dto));
    }


    @Operation(summary = "Token oluşturma işlemi",
            description = "Belirli kullanıcı verileri kullanılarak bir token oluşturur.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/search-create-token")
    public ResponseEntity<String> createToken(@RequestBody SelectUserCreateTokenDto dto) {
        return ResponseEntity.ok(studentService.createToken(dto));
    }

    @Operation(summary = "Token'dan kullanıcı kimliği alınması işlemi",
            description = "Belirli bir token'dan kullanıcı kimliğini çıkarmak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/get-id-from-token/{token}")
    public ResponseEntity<String> getIdFromToken(@PathVariable String token) {
        return ResponseEntity.ok(studentService.getIdFromToken(token));
    }

    @Operation(summary = "Öğrenci profil bilgilerini bulma işlemi",
            description = "Belirli bir token kullanarak öğrencinin profil bilgilerini bulmak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/find-student-profile/{token}")
    public ResponseEntity<FindStudentProfileResponseDto> findStudentProfile(@PathVariable String token) {
        return ResponseEntity.ok(studentService.findStudentProfile(token));
    }

    @Operation(summary = "Öğrenci listesini kaydetme işlemi",
            description = "Bir öğrenci listesini kaydetmek için kullanılır.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/save-student-list")
    public ResponseEntity<Boolean> saveStudentList(@RequestBody List<SaveStudentRequestDto> dtoList) {
        return ResponseEntity.ok(studentService.saveStudentList(dtoList));
    }

    @Operation(summary = "Öğrencinin ad ve soyadını öğrenci kimliği ile alma işlemi",
            description = "Belirli bir öğrenci kimliği ile öğrencinin ad ve soyadını almak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/get-name-and-surname-with-id/{studentId}")
    public ResponseEntity<String> getNameAndSurnameWithId(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getNameAndSurnameWithId(studentId));
    }

    @Operation(summary = "Grup adına göre öğrencileri bulma işlemi",
            description = "Belirli bir grup adına göre öğrencileri bulmak için kullanılır.")
    @GetMapping("/find-by-group-name-list/{groupName}")
    public ResponseEntity<List<FindByGroupNameResponseDto>> findByGroupNameList(@PathVariable String groupName) {
        return ResponseEntity.ok(studentService.findByGroupNameList(groupName));
    }


    @Operation(summary = "Öğrencinin transkript bilgilerini alma işlemi",
            description = "Belirtilen bir öğrenci kimliği ile ilişkilendirilmiş transkript bilgilerini almak için kullanılır.")
    @GetMapping("/get-transcript-info/{token}")
    public ResponseEntity<TranscriptInfo> getTranscriptInfoByStudent(@PathVariable String token) {
        return ResponseEntity.ok(studentService.getTranscriptInfoByStudent(token));
    }


    @Operation(summary = "Staj yapmayan tüm öğrencileri alma işlemi",
            description = "Staj yapmayan öğrencilerin bilgilerini almak için kullanılır.")
    @PostMapping("/get-all-students-without-internship")
    public ResponseEntity<List<GroupStudentResponseDto>> getAllStudentsWithoutInternship(@RequestBody GroupStudentRequestDto dto) {
        return ResponseEntity.ok(studentService.getAllStudentsWithoutInternship(dto));
    }


    @Operation(summary = "Öğrencinin staj durumunu aktif yapma işlemi",
            description = "Belirli bir öğrencinin staj durumunu aktif yapmak için kullanılır.")
    @PutMapping("/update-student-internship-status-to-active/{studentId}")
    public ResponseEntity<Boolean> updateStudentInternShipStatusToActive(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.updateStudentInternShipStatus(studentId));
    }


    @Operation(summary = "Öğrencinin staj durumunu silinmiş olarak güncelleme işlemi",
            description = "Belirli bir öğrencinin staj durumunu silinmiş olarak güncellemek için kullanılır.")
    @PutMapping("/update-student-internship-status-to-deleted/{studentId}")
    public ResponseEntity<Boolean> updateStudentInternShipStatusToDeleted(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.updateStudentInternShipStatusToDeleted(studentId));
    }

    @Operation(summary = "Öğrenci için yönetici kaydı oluşturma işlemi",
            description = "Belirli bir öğrenci için yönetici kaydı oluşturulması için kullanılır.")
    @Hidden
    @PostMapping("/save-manager-for-student-service")
    public ResponseEntity<String> registerManagerForStudent(@RequestBody RegisterRequestDto dto) {
        return ResponseEntity.ok(studentService.registerManagerForStudent(dto));
    }


    @Operation(summary = "Token'dan kullanıcının ad ve soyadını getirme işlemi",
            description = "Verilen token'dan kullanıcının ad ve soyadını getirmek için kullanılır.")
    @GetMapping("/get-user-name-and-surname-from-token-for-login/{token}")
    public ResponseEntity<GetNameAndSurnameByIdResponseDto> getUserNameAndSurnameFromToken(@PathVariable String token) {
        return ResponseEntity.ok(studentService.getUserNameAndSurnameFromToken(token));
    }

    @Operation(summary = "Kullanıcının şifresini değiştirme işlemi",
            description = "Kullanıcının şifresini değiştirmek için kullanılır. " +
                    "Kullanıcının oturum açma token'ı ve yeni şifre bilgileri parametre olarak alınır.")
    @PostMapping("/change-password-from-user/{token}")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequestDto dto, @PathVariable String token) {
        return ResponseEntity.ok(studentService.changePassword(dto, token));
    }


    @Operation(summary = "Öğrenci için grup adlarını bulma işlemi",
            description = "Belirtilen öğrenci kimliği ile ilişkilendirilmiş grup adlarını döndürür.")
    @Hidden
    @GetMapping("/get-group-name-for-student/{studentId}")
    public ResponseEntity<List<String>> findGroupNameForStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.findGroupNameForStudent(studentId));
    }

    @Operation(summary = "Öğrencinin profil bilgilerini getirme işlemi",
            description = "Belirtilen token ile ilişkilendirilmiş öğrencinin profil bilgilerini döndürür.")
    @GetMapping("/get-student-profile-for-profile-page/{token}")
    public ResponseEntity<FindStudentProfileResponseDto> getStudentProfile(@PathVariable String token) {
        return ResponseEntity.ok(studentService.getStudentProfile(token));
    }


    @Operation(summary = "Öğrencinin profil fotoğrafını kaydetme işlemi",
            description = "Belirtilen DTO içindeki bilgilere göre öğrencinin profil fotoğrafını kaydeder.")
    @PostMapping("/save-profile-image")
    public ResponseEntity<Boolean> saveProfileImage(@RequestBody SaveProfileImageRequestDto dto) {
        return ResponseEntity.ok(studentService.saveProfileImage(dto));
    }

    @Operation(summary = "Öğrencinin profil fotoğrafını alma işlemi",
            description = "Belirtilen token ile ilişkili öğrencinin profil fotoğrafını getirir.")
    @GetMapping("/get-student-profile-image/{token}")
    public ResponseEntity<String> getStudentProfileImage(@PathVariable String token) {
        return ResponseEntity.ok(studentService.getProfileImage(token));
    }

    @Operation(summary = "Base kısmında öğrenci verileri çekme işlemi",
            description = "Base kısmındaki öğrenci verilerini çekip mongoya kayıt yapar.")
    @GetMapping("/get-all-base-students")
    public ResponseEntity<String> getAllBaseStudents() {
        try {
            studentService.getAllBaseStudents();
            return ResponseEntity.ok("Öğrenci verileri eklendi");
        } catch (Exception e) {
            throw new UserServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Operation(summary = "Aday Eklemek için  excel şablonu indirme işlemi",
            description = "Aday Eklemek için gerekli şablon buradan indirilir.")
    @GetMapping("/download-excel")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        String fileName = "aday-ekleme-sablonu.xlsx";
        Resource resource = new ClassPathResource(fileName);
        if (!resource.exists()) {
            throw new RuntimeException("Dosya bulunamadı!");
        }
        InputStreamResource inputStreamResource = new InputStreamResource(resource.getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=aday-ekleme-sablonu.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }

    @Operation(summary = "Excel ile toplu aday ekleme işlemi ",
            description = "Adayları Eklemek için excel buradan yüklenir.")
    @PostMapping("/upload-excel")
    public ResponseEntity<InputStreamResource> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] workbookBytes = studentService.readExcel(file);

        if (workbookBytes != null) {
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(workbookBytes)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.setContentDispositionFormData("attachment", "kaydedilemeyen-adaylar.xlsx");

                return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

    }
}
