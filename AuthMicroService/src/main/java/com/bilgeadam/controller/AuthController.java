package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.MessageResponseDto;
import com.bilgeadam.service.AuthService;
import feign.Param;
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

    @PostMapping(REGISTER)
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponseDto>register( @RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(FORGOT_PASSWORD)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponseDto>forgotMyPassword(@RequestParam String email){
        return ResponseEntity.ok(authService.forgotMyPassword(email));
    }
    @PostMapping(LOGIN)
    @CrossOrigin("*")
    public ResponseEntity<LoginResponseDto>login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping(RESET_PASSWORD)
    @CrossOrigin("*")
    public ResponseEntity<Boolean>resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto) {
        return ResponseEntity.ok(authService.resetPassword(dto));
    }
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

}
