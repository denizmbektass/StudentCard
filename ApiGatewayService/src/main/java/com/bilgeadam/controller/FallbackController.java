package com.bilgeadam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class FallbackController {
    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuthService(){
        return ResponseEntity.ok("Auth servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/user")
    public ResponseEntity<String> fallbackUserService(){
        return ResponseEntity.ok("User servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/mail")
    public ResponseEntity<String> fallbackMailService(){
        return ResponseEntity.ok("Mail servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card")
    public ResponseEntity<String> fallbackCardService(){
        return ResponseEntity.ok("Card servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
}
