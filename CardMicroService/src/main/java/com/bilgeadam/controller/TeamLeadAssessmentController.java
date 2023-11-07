package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateTeamLeadAssesmentRequestDto;
import com.bilgeadam.dto.request.UpdateTeamLeadAssessmentRequestDto;
import com.bilgeadam.dto.response.GetTeamLeadAssessmentDetailsResponseDto;
import com.bilgeadam.service.TeamLeadAssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.bilgeadam.constants.ApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(TEAM_LEAD_ASSESSMENT)
public class TeamLeadAssessmentController {
    private final TeamLeadAssessmentService teamLeadAssessmentService;

    @Operation(summary = "Takım lideri görüşü kaydetme işlemi", description = "Staj menüsündeki takım lideri  " +
            "sayfasında yapılan takım lideri görüşü kaydetme işlemidir.")
    @PostMapping(SAVE_TEAM_LEAD_ASSESSMENT)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveTeamLeadAssessment(@Valid @RequestBody CreateTeamLeadAssesmentRequestDto dto) {
        return ResponseEntity.ok(teamLeadAssessmentService.saveTrainerAssessment(dto));
    }

    @Operation(summary = "Takım lideri görüşü sayfasında Takım lideri görüşünü günceller", description = "Staj menüsünde " +
            "Takım lideri görüşü sayfasında öğrencinin daha önce girilmiş Takım lideri görüşü varsa üstüne kaydeder")
    @PutMapping(UPDATE_TEAM_LEAD_ASSESSMENT)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateTeamLeadAssessment(@Valid @RequestBody UpdateTeamLeadAssessmentRequestDto dto) {
        return ResponseEntity.ok(teamLeadAssessmentService.updateTrainerAssessment(dto));
    }
    @Operation(summary = "Kariyer eğitimi sayfasında mevcut puanları getirir", description = "İstihdam menüsünde " +
            "Kariyer Eğitimi başlığında öğrencinin önceden kaydedilmiş kariyer eğitimi puan değerleri varsa getirir")
    @GetMapping(GET_TEAM_LEAD_ASSESSMENT_DETAILS + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetTeamLeadAssessmentDetailsResponseDto> getTeamLeadAssessmentDetails(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(teamLeadAssessmentService.getTeamLeadsAssessmentDetails(studentId));
    }
}
