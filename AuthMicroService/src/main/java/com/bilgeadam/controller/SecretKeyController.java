package com.bilgeadam.controller;

import com.bilgeadam.dto.request.LoginQrRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.service.SecretKeyService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/secret-key")
public class SecretKeyController {
    private final SecretKeyService secretKeyService;

    @Operation(summary = "Gizli anahtar oluşturma işlemi",
            description = "Belirtilen token kullanılarak gizli anahtar oluşturur.")
    @CrossOrigin
    @GetMapping("/generate-secret-key/{token}")
    public ResponseEntity<String> generateSecretKey(@PathVariable String token){
        return ResponseEntity.ok(secretKeyService.generateSecretKey(token));
    }



    @Operation(summary = "TOTP kodu oluşturma işlemi",
            description = "Belirtilen token kullanılarak TOTP kodu oluşturur.")
    @CrossOrigin
    @GetMapping("/generate-totp-code/{token}")
    public ResponseEntity<String> generateTotpCode(@PathVariable String token){
        return ResponseEntity.ok(secretKeyService.generateTotpCode(token));
    }



    @Operation(summary = "QR kodu oluşturma işlemi",
            description = "Belirtilen token kullanılarak QR kodu oluşturur.")
    @CrossOrigin
    @GetMapping("/generate-qr-code/{token}")
    public ResponseEntity<String> generateQRCode(@PathVariable String token) throws IOException, WriterException {
        return ResponseEntity.ok(secretKeyService.generateQRCode(token));
    }



    @Operation(summary = "QR kod ile giriş işlemi",
            description = "QR kod kullanılarak giriş işlemi yapar.")
    @CrossOrigin
    @PostMapping("/login-with-qr-code")
    public ResponseEntity<LoginResponseDto> loginWithQrCode(@RequestBody LoginQrRequestDto dto) {
        return ResponseEntity.ok(secretKeyService.loginWithQrCode(dto));
    }







}
