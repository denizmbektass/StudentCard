package com.bilgeadam.controller;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.dto.request.UpdateInternshipRequestDto;
import com.bilgeadam.dto.response.InternshipResponseDto;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import com.bilgeadam.service.InternshipSuccessRateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERNSHIP)
@RequiredArgsConstructor
public class InternshipSuccessRateController {
    private final InternshipSuccessRateService internshipSuccessRateService;

    @Operation(summary = "Öğrenci için başarı oranı ve yorum ekleme işlemi",
            description = "Belirtilen DTO kullanılarak bir öğrenci için başarı oranı ve yorum ekler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','INTERNSHIP_TEAM')")
    @PostMapping(ADD_SCORE_AND_COMMENT)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> addScoreAndCommentForStudent(@RequestBody InternshipSuccessRateRequestDto dto) {
        return ResponseEntity.ok(internshipSuccessRateService.addScoreAndCommentForStudent(dto));
    }


    @Operation(summary = "Kullanıcıya ait stajları alma işlemi",
            description = "Belirtilen token kullanılarak bir kullanıcıya ait stajları alır.")
    @GetMapping("/find-all-internship-with-user/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<InternshipResponseDto>> findAllInternshipWithUser(@PathVariable String token) {
        return ResponseEntity.ok(internshipSuccessRateService.findAllInternshipWithUser(token));
    }

    @Operation(summary = "Seçilen stajı silme işlemi",
            description = "Belirtilen staj ID'sine sahip stajı siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','INTERNSHIP_TEAM')")
    @DeleteMapping("/delete-selected-internship/{internshipId}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> deleteSelectedInternship(@PathVariable String internshipId) {
        return ResponseEntity.ok(internshipSuccessRateService.deleteSelectedInternship(internshipId));
    }


    @Operation(summary = "Seçilen stajı güncelleme işlemi",
            description = "Belirtilen güncelleme isteği DTO'su kullanılarak seçilen stajı günceller.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','INTERNSHIP_TEAM')")
    @PutMapping("/update-selected-internship")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateSelectedInternship(@RequestBody UpdateInternshipRequestDto dto) {
        return ResponseEntity.ok(internshipSuccessRateService.updateSelectedInternship(dto));
    }
}
