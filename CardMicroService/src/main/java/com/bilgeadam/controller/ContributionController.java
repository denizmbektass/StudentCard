package com.bilgeadam.controller;
import com.bilgeadam.dto.request.SaveContributionRequestDto;
import com.bilgeadam.dto.request.UpdateContributionRequestDto;
import com.bilgeadam.dto.response.GetContributionResponseDto;
import com.bilgeadam.service.ContributionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.bilgeadam.constants.ApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(CONTRIBUTION)
public class ContributionController {
    private final ContributionService contributionService;

    @Operation(summary = "Katkı değerlendirmesini  kaydetme işlemi", description = "Staj Başarı Puanı menüsündeki Katkı  " +
                "sayfasında yapılan değerlendirmeyi kaydetme işlemidir.")
    @PostMapping(SAVE_CONTRIBUTION)
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('internship_team:write')")
    public ResponseEntity<Boolean> saveContribution(@Valid @RequestBody SaveContributionRequestDto dto) {
        return ResponseEntity.ok(contributionService.saveContribution(dto));
    }

    @Operation(summary = "Katkı değerlendirmesini güncelleme işlemi", description = "Staj Başarı Puanı menüsündeki " +
            "Katkı sayfasında yapılan değerlendirmeyi öğrencinin daha önce girilmiş katkı değerlendirmesi varsa üzerine kaydeder.")
    @PutMapping(UPDATE_CONTRIBUTION)
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('internship_team:write')")
    public ResponseEntity<Boolean> updateContribution(@Valid @RequestBody UpdateContributionRequestDto dto) {
        return ResponseEntity.ok(contributionService.updateContribution(dto));
    }

    @Operation(summary = "Katkı sayfasında kayıtlı puanları getirme işlemi", description = "Staj Başarı Puanı menüsündeki " +
            "Katkı sayfasında öğrencinin önceden kayıtlı olan katkı değerlendirmesi varsa getirir.")
    @GetMapping(GET_CONTRIBUTION + "/{studentId}")
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<GetContributionResponseDto> getContribution(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(contributionService.getContribution(studentId));
    }

    @Operation(summary = "Katkı sayfasında girilen değerlere göre hesaplanan ortalama puanı getirme işlemi", description = "Staj Başarı Puanı menüsündeki " +
            "Katkı sayfasında girilen değerlere göre öğrencinin katkı değerlendirmesi puan bilgisini getirir.")
    @GetMapping(GET_TOTAL_SCORE_CONTRIBUTION + "/{studentId}")
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Double> calculateAndGetTotalScoreContribution(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(contributionService.calculateAndGetTotalScoreContribution(studentId));
    }
}
