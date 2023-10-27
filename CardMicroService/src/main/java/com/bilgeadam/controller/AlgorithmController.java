package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateAlgorithmRequestDto;
import com.bilgeadam.dto.request.UpdateAlgorithmRequestDto;
import com.bilgeadam.dto.response.AlgorithmResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.AlgorithmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import static com.bilgeadam.constants.ApiUrls.*;


@RestController
@RequestMapping(ALGORITHM)
@RequiredArgsConstructor
public class AlgorithmController {
    private final AlgorithmService algorithmService;
    @Operation(summary = "Algoritma oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak bir algoritma sonucu oluşturur.")
    @CrossOrigin("*")
    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createAlgorithmScore(@RequestBody @Valid CreateAlgorithmRequestDto dto){
        algorithmService.createAlgorithmScore(dto);
        return ResponseEntity.ok(new MessageResponse("Algoritma sonucu başarıyla kaydedildi.."));
    }

    @Operation(summary = "Algoritma güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak bir algoritma sonucunu günceller.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateAlgoritmScore(@RequestBody UpdateAlgorithmRequestDto dto){
        algorithmService.updateAlgorithmScore(dto);
        return ResponseEntity.ok(new MessageResponse("Algoritma başarıyla güncellendi.."));
    }

    @Operation(summary = "Algoritma silme işlemi",
            description = "Belirtilen DTO kullanılarak bir algoritmayı siler.")
    @DeleteMapping(DELETE+"/{algorithmId}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteAlgorithm(@PathVariable String algorithmId){
        algorithmService.deleteAlgorithm(algorithmId);
        return ResponseEntity.ok(new MessageResponse("Algoritma başarıyla silindi.."));
    }
    @Operation(summary = "Öğrencinin Algoritmasını bulma",
            description = "Belirtilen token kullanılarak Bitirme projesini getirir.")
    @GetMapping(FIND_ALGORITHM+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<AlgorithmResponseDto> findAlgorithm(@PathVariable String token){
        return  ResponseEntity.ok(algorithmService.getAlgorithm(token));
    }



}
