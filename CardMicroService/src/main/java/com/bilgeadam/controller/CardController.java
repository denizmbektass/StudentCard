package com.bilgeadam.controller;

import com.bilgeadam.dto.response.CardResponseDto;
import com.bilgeadam.dto.response.EducationScoreDetailsDto;
import com.bilgeadam.dto.response.StudentChoiceResponseDto;
import com.bilgeadam.dto.response.TranscriptResponseDto;
import com.bilgeadam.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(CARD)
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @Operation(summary = "Öğrenciye ait kartı alma işlemi",
            description = "Belirtilen token kullanılarak öğrenciye ait kartı alır.")
    @CrossOrigin("*")
    @GetMapping("/get-card/{token}")
    public ResponseEntity<CardResponseDto> getCardByStudent(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getCardByStudent(token));
    }


    @Operation(summary = "Öğrenci için kart parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak öğrenci için kart parametrelerini alır.")
    @CrossOrigin("*")
    @GetMapping("/get-card-parameter-for-student/{token}")
    public ResponseEntity<Map<String, Integer>> getCardParameterForStudent(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getCardParameterForStudent(token));
    }

    @CrossOrigin("*")
    @GetMapping("/get-transcript/{token}")
    public ResponseEntity<TranscriptResponseDto> getTranscriptByStudent(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getTranscriptByStudent(token));
    }

    @Operation(summary = "Eğitim kartı için parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak eğitim için kart bilgilerini almak.")
    @GetMapping("/get-education-score-details/{token}")
    @CrossOrigin("*")
    public ResponseEntity<EducationScoreDetailsDto> getEducationDetails(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getEducationDetails(token));
    }

    @Operation(summary = "Ögrenci Seçme kartı için parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak öğrenci için kart bilgilerini almak.")
    @GetMapping("/get-student-choice-score-details/{token}")
    @CrossOrigin("*")
    public ResponseEntity<StudentChoiceResponseDto> getStudentChoiceDetails(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getStudentChoiceDetails(token));
    }

}
