package com.bilgeadam.controller;

import com.bilgeadam.dto.request.LoginQrRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.service.SecretKeyService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secret-key")
public class SecretKeyController {
    private final SecretKeyService secretKeyService;

    @CrossOrigin
    @GetMapping("/generate-secret-key/{token}")
    public ResponseEntity<String> generateSecretKey(@PathVariable String token){
        return ResponseEntity.ok(secretKeyService.generateSecretKey(token));
    }

    @CrossOrigin
    @GetMapping("/generate-totp-code/{token}")
    public ResponseEntity<String> generateTotpCode(@PathVariable String token){
        return ResponseEntity.ok(secretKeyService.generateTotpCode(token));
    }

    @CrossOrigin
    @GetMapping("/generate-qr-code/{token}")
    public ResponseEntity<String> generateQRCode(@PathVariable String token) throws IOException, WriterException {
        return ResponseEntity.ok(secretKeyService.generateQRCode(token));
    }

    @CrossOrigin
    @PostMapping("/login-with-qr-code")
    public ResponseEntity<LoginResponseDto> loginWithQrCode(@RequestBody LoginQrRequestDto dto) {
        return ResponseEntity.ok(secretKeyService.loginWithQrCode(dto));
    }




}
