package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.ChangePasswordResponseDto;
import com.bilgeadam.dto.response.GetAuthInfoForChangePassword;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.MessageResponseDto;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.service.AuthService;
import feign.Param;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Kullanıcı kaydı yapma işlemi",
            description = "Kullanıcı kaydı yapmak için kullanılır.")
    @PostMapping(REGISTER)
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponseDto>register( @RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }


    @Operation(summary = "Parolamı Unuttum işlemi",
            description = "Kullanıcının parolasını sıfırlamak için kullanılır.")
    @PostMapping(FORGOT_PASSWORD)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponseDto>forgotMyPassword(@RequestParam String email){
        return ResponseEntity.ok(authService.forgotMyPassword(email));
    }


    @Operation(summary = "Kullanıcı giriş işlemi",
            description = "Kullanıcının sisteme giriş yapması için kullanılır.")
    @PostMapping(LOGIN)
    @CrossOrigin("*")
    public ResponseEntity<LoginResponseDto>login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }


    @Operation(summary = "Şifre sıfırlama işlemi",
            description = "Kullanıcının şifresini sıfırlamak için kullanılır.")
    @PostMapping(RESET_PASSWORD)
    @CrossOrigin("*")
    public ResponseEntity<Boolean>resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto) {
        return ResponseEntity.ok(authService.resetPassword(dto));
    }


    @Operation(summary = "Kullanıcı aktivasyon işlemi",
            description = "Kullanıcının hesabını aktive etmek için kullanılır.")
    @GetMapping(ACTIVATE_USER + "/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean>activateUser(@PathVariable String token) throws URISyntaxException {
        System.out.println(token);
        if(authService.activateUser(token)){
            System.out.println("ASDASHDIQWARDAWER");
            URI forgotPasswordSuccessful = new URI("http://localhost:3000/activation-link/"+ token);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(forgotPasswordSuccessful);

            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }
        return ResponseEntity.ok(authService.activateUser(token));
    }


    @Operation(summary = "Kullanıcının şifre değiştirme için kimlik doğrulama bilgilerini getirme işlemi",
            description = "Kullanıcının şifre değiştirme işlemi için gerekli kimlik doğrulama bilgilerini getirir.")
    @Hidden
    @CrossOrigin("*")
    @GetMapping("/get-auth-info-for-user-change-password/{userId}")
    public ResponseEntity<GetAuthInfoForChangePassword> getAuthInfoForChangePassword(@PathVariable String userId){
        return ResponseEntity.ok(authService.getAuthInfoForChangePassword(userId));
    }


    @Operation(summary = "Kullanıcının şifresini değiştirme işlemi",
            description = "Kullanıcının mevcut şifresini değiştirmek için kullanılır.")
    @PostMapping("/change-password-from-user")
    public ResponseEntity<Boolean> changePasswordFromUser(@RequestBody ChangePasswordResponseDto dto){
        return ResponseEntity.ok(authService.changePasswordForAuth(dto));
    }



    @Operation(summary = "Token'dan kullanıcının rollerini getirme işlemi",
            description = "Verilen token'dan kullanıcının rollerini getirir.")
    @GetMapping("/get-role-from-token/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<ERole>> getRoleFromToken(@PathVariable String token){
        return ResponseEntity.ok(authService.getRoleFromToken(token));
    }



    @Operation(summary = "Öğrenci ve eğitmen kaydı işlemi",
            description = "Öğrenci ve eğitmenin kaydını yapmak için kullanılır.")
    @PostMapping("/register-student-and-trainer")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponseDto>registerStudentAndTrainer( @RequestBody @Valid RegisterStudentAndTrainerRequestDto dto){
        return ResponseEntity.ok(authService.registerStudentAndTrainer(dto));
    }



}
