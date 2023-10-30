package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;

import com.bilgeadam.dto.response.*;
import com.bilgeadam.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERVIEW)
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @Operation(summary = "Aday mülakatı kaydetme işlemi", description = "Öğrenci seçme menüsündeki Aday Mülakatı" +
            " başlığından yapılan aday mülakatı kaydetme işlemidir.")
    @PostMapping(SAVE_CANDIDATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveCandidateInterview(@Valid @RequestBody SaveInterviewRequestDto dto) {
        return ResponseEntity.ok(interviewService.saveCandidateInterview(dto));
    }

    @Operation(summary = "Aday mülakatı sayfasında mevcut puanları getirir", description = "Öğrenci seçme menüsünde " +
            "Aday Mülakatı başlığında öğrencinin daha önceden kaydedilen aday mülakatı puan değerleri varsa getirir")
    @GetMapping(GET_CANDIDATE_INTERVIEW + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetCandidateInterviewResponseDto> getCandidateInterview(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(interviewService.getCandidateInterview(studentId));
    }

    @Operation(summary = "Aday mülakatı sayfasında aday mülakatını günceller", description = "Öğrenci seçme" +
            " menüsünde Aday Mülakatı başlığında öğrencinin daha önce girilmiş mülakatı varsa üstüne kaydeder")
    @PutMapping(UPDATE_CANDIDATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateCandidateInterview(@Valid @RequestBody UpdateCandidateInterviewRequestDto dto) {
        return ResponseEntity.ok(interviewService.updateCandidateInterview(dto));
    }

    @Operation(summary = "Aday mülakatı sayfasında aday mülakatı sayısını döner", description = "Öğrenci seçme " +
            "menüsünde Aday Mülakatı başlığında öğrencinin DB'de kayıtlı aday mülakatı sayısını döner (0 ya da 1)")
    @GetMapping(GET_CANDIDATE_INTERVIEW_COUNT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getCandidateInterviewCount(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(interviewService.getCandidateInterviewCount(studentId));
    }

    @Operation(summary = "Aday mülakatı sayfasında ortalama puanı döner", description = "Öğrenci seçme menüsünde " +
            "Aday Mülakatı başlığında öğrencinin ortalama aday mülakatı puan bilgisini döner")
    @GetMapping(GET_CANDIDATE_INTERVIEW_AVERAGE_POINT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getCandidateInterviewAveragePoint(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(interviewService.getCandidateInterviewAveragePoint(studentId));
    }

}
