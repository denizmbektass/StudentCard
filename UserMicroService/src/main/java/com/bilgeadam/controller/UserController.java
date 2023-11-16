package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @Operation(summary = "Kullanıcı güncelleme işlemi",
            description = "Belirtilen kullanıcı bilgilerini günceller.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean>updateUser(@RequestBody @Valid UserUpdateRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    @Operation(summary = "Kullanıcıyı pasif duruma getirme işlemi",
            description = "Belirtilen kullanıcıyı pasif duruma getirir.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(DO_PASSIVE)
    public ResponseEntity<Boolean> doPassive(@RequestParam String id){
        return ResponseEntity.ok(userService.doPassive(id));
    }

    @Operation(summary = "Güvenli kullanıcı silme işlemi",
            description = "Belirtilen kullanıcıyı güvenli bir şekilde siler.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(SAFE_DELETE)
    public ResponseEntity<Boolean> safeDelete(@RequestParam String id){
        return ResponseEntity.ok(userService.safeDelete(id));
    }

    @Operation(summary = "Kullanıcı kaydetme işlemi",
            description = "Belirtilen kullanıcı bilgilerini kaydeder.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(SAVE)
    public  ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto dto){
        return ResponseEntity.ok(userService.save(dto));
    }


    @Operation(summary = "E-posta hatırlatıcı için eğitmenleri getirme işlemi",
            description = "E-posta hatırlatıcı için eğitmenlerin listesini getirir.")
    @CrossOrigin("*")
    @GetMapping("/mail-reminder-get-trainers")
    public  ResponseEntity<List<TrainersMailReminderDto>> getTrainers(){
        return ResponseEntity.ok(userService.getTrainers());
    }


    @Operation(summary = "E-posta hatırlatıcı için yöneticileri getirme işlemi",
            description = "E-posta hatırlatıcı için yöneticilerin listesini getirir.")
    @CrossOrigin("*")
    @GetMapping("/mail-reminder-get-masters")
    public  ResponseEntity<List<MastersMailReminderDto>> getMasters(){
        return ResponseEntity.ok(userService.getMasters());
    }


    @Operation(summary = "E-posta hatırlatıcı için öğrencileri getirme işlemi",
            description = "E-posta hatırlatıcı için öğrencilerin listesini getirir.")
    @CrossOrigin("*")
    @GetMapping("/mail-reminder-get-students")
    public  ResponseEntity<List<StudentsMailReminderDto>> getStudents(){
        return ResponseEntity.ok(userService.getStudents());
    }

    @Operation(summary = "Kullanıcı arama işlemi",
            description = "Belirli kriterlere göre kullanıcıları aramak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @PostMapping("/search-user")
    public  ResponseEntity<List<User>> searchUser(@RequestBody SearchUserRequestDto dto){
        return  ResponseEntity.ok(userService.searchUser(dto));
    }


    @Operation(summary = "Token oluşturma işlemi",
            description = "Belirli kullanıcı verileri kullanılarak bir token oluşturur.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @PostMapping("/search-create-token")
    public  ResponseEntity<String> createToken( @RequestBody SelectUserCreateTokenDto dto){
        return  ResponseEntity.ok(userService.createToken(dto));
    }

    @Operation(summary = "Token'dan kullanıcı kimliği alınması işlemi",
            description = "Belirli bir token'dan kullanıcı kimliğini çıkarmak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @PostMapping("/get-id-from-token/{token}")
    public  ResponseEntity<String> getIdFromToken(@PathVariable String token){
        return  ResponseEntity.ok(userService.getIdFromToken(token));
    }

    @Operation(summary = "Öğrenci profil bilgilerini bulma işlemi",
            description = "Belirli bir token kullanarak öğrencinin profil bilgilerini bulmak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @GetMapping ("/find-student-profile/{token}")
    public  ResponseEntity<FindStudentProfileResponseDto> findStudentProfile(@PathVariable String token){
        return  ResponseEntity.ok(userService.findStudentProfile(token));
    }

    @Operation(summary = "Kullanıcı listesini kaydetme işlemi",
            description = "Bir kullanıcı listesini kaydetmek için kullanılır.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping("/save-user-list")
    public  ResponseEntity<Boolean> saveUserList(@RequestBody List<UserRequestDto> dtoList){
        return  ResponseEntity.ok(userService.saveUserList(dtoList));
    }

    @Operation(summary = "Kullanıcının ad ve soyadını kullanıcı kimliği ile alma işlemi",
            description = "Belirli bir kullanıcı kimliği ile kullanıcının ad ve soyadını almak için kullanılır.")
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @GetMapping("/get-name-and-surname-with-id/{userId}")
    public ResponseEntity<String> getNameAndSurnameWithId(@PathVariable String userId){
        return ResponseEntity.ok(userService.getNameAndSurnameWithId(userId));
    }

    @Operation(summary = "Grup adına göre kullanıcıları bulma işlemi",
            description = "Belirli bir grup adına göre kullanıcıları bulmak için kullanılır.")
    @CrossOrigin("*")
    @GetMapping("/find-by-group-name-list/{groupName}")
    public ResponseEntity<List<FindByGroupNameResponseDto>> findByGroupNameList(@PathVariable String groupName) {
        return  ResponseEntity.ok(userService.findByGroupNameList(groupName));
    }


    @Operation(summary = "Kullanıcının transkript bilgilerini alma işlemi",
            description = "Belirtilen bir kullanıcı kimliği ile ilişkilendirilmiş transkript bilgilerini almak için kullanılır.")
    @CrossOrigin("*")
    @GetMapping("/get-transcript-info/{token}")
    public ResponseEntity<TranscriptInfo> getTranscriptInfoByUser(@PathVariable String token){
        return ResponseEntity.ok(userService.getTranscriptInfoByUser(token));
    }


    @Operation(summary = "Staj yapmayan tüm öğrencileri alma işlemi",
            description = "Staj yapmayan öğrencilerin bilgilerini almak için kullanılır.")
    @CrossOrigin("*")
    @PostMapping("/get-all-students-without-internship")
    public ResponseEntity<List<GroupStudentResponseDto>> getAllStudentsWithoutInternship(@RequestBody GroupStudentRequestDto dto){
        return ResponseEntity.ok(userService.getAllStudentsWithoutInternship(dto));
    }


    @Operation(summary = "Kullanıcının staj durumunu aktif yapma işlemi",
            description = "Belirli bir kullanıcının staj durumunu aktif yapmak için kullanılır.")
    @CrossOrigin("*")
    @PutMapping("/update-user-internship-status-to-active/{userId}")
    public ResponseEntity<Boolean> updateUserInternShipStatusToActive(@PathVariable String userId){
        return ResponseEntity.ok(userService.updateUserInternShipStatus(userId));
    }


    @Operation(summary = "Kullanıcının staj durumunu silinmiş olarak güncelleme işlemi",
            description = "Belirli bir kullanıcının staj durumunu silinmiş olarak güncellemek için kullanılır.")
    @CrossOrigin("*")
    @PutMapping("/update-user-internship-status-to-deleted/{userId}")
    public ResponseEntity<Boolean> updateUserInternShipStatusToDeleted(@PathVariable String userId){
        return ResponseEntity.ok(userService.updateUserInternShipStatusToDeleted(userId));
    }

    @Operation(summary = "Kullanıcı için yönetici kaydı oluşturma işlemi",
            description = "Belirli bir kullanıcı için yönetici kaydı oluşturulması için kullanılır.")
    @Hidden
    @PostMapping("/save-manager-for-user-service")
    @CrossOrigin("*")
     public ResponseEntity<String> registerManagerForUser(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerManagerForUser(dto));
    }


    @Operation(summary = "Token'dan kullanıcının ad ve soyadını getirme işlemi",
            description = "Verilen token'dan kullanıcının ad ve soyadını getirmek için kullanılır.")
    @GetMapping("/get-user-name-and-surname-from-token-for-login/{token}")
    @CrossOrigin("*")
    public ResponseEntity<GetNameAndSurnameByIdResponseDto> getUserNameAndSurnameFromToken(@PathVariable String token){
        return ResponseEntity.ok(userService.getUserNameAndSurnameFromToken(token));
    }

    @Operation(summary = "Kullanıcının şifresini değiştirme işlemi",
            description = "Kullanıcının şifresini değiştirmek için kullanılır. " +
                    "Kullanıcının oturum açma token'ı ve yeni şifre bilgileri parametre olarak alınır.")
    @PostMapping("/change-password-from-user/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequestDto dto,@PathVariable String token){
        return ResponseEntity.ok(userService.changePassword(dto,token));
    }


    @Operation(summary = "Öğrenci için grup adlarını bulma işlemi",
            description = "Belirtilen öğrenci kimliği ile ilişkilendirilmiş grup adlarını döndürür.")
    @Hidden
    @GetMapping("/get-group-name-for-student/{userId}")
    public ResponseEntity<List<String>> findGroupNameForStudent(@PathVariable String userId){
        return ResponseEntity.ok(userService.findGroupNameForStudent(userId));
    }

    @Operation(summary = "Kullanıcının profil bilgilerini getirme işlemi",
            description = "Belirtilen token ile ilişkilendirilmiş kullanıcının profil bilgilerini döndürür.")
    @GetMapping("/get-user-profile-for-profile-page/{token}")
    @CrossOrigin("*")
    public ResponseEntity<FindStudentProfileResponseDto> getUserProfile(@PathVariable String token){
        return ResponseEntity.ok(userService.getUserProfile(token));
    }


    @Operation(summary = "Kullanıcının profil fotoğrafını kaydetme işlemi",
            description = "Belirtilen DTO içindeki bilgilere göre kullanıcının profil fotoğrafını kaydeder.")
    @PostMapping("/save-profile-image")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveProfileImage(@RequestBody SaveProfileImageRequestDto dto){
        return ResponseEntity.ok(userService.saveProfileImage(dto));
    }

    @Operation(summary = "Kullanıcının profil fotoğrafını alma işlemi",
            description = "Belirtilen token ile ilişkili kullanıcının profil fotoğrafını getirir.")
    @GetMapping("/get-user-profile-image/{token}")
    @CrossOrigin("*")
    public ResponseEntity<String> getUserProfileImage(@PathVariable String token){
        return ResponseEntity.ok(userService.getProfileImage(token));
    }

}
