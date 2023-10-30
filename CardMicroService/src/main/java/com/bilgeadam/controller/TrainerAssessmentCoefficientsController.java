package com.bilgeadam.controller;

import com.bilgeadam.dto.request.UpdateTrainerAssessmentCoefficientsRequestDto;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import com.bilgeadam.service.TrainerAssessmentCoefficientsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TRAINER_ASSESSMENT_COEFFICIENTS)
@RequiredArgsConstructor
public class TrainerAssessmentCoefficientsController {

    private final TrainerAssessmentCoefficientsService trainerAssessmentCoefficientsService;

    @Operation(summary = "Eğitmen değerlendirme puan katsayılarının güncelleme ve kaydedilme işlemi",
            description = "Belirtilen eğitmen değerlendirme puan katsayılarının güncelleme isteği DTO'su kullanılarak bir eğitmen değerlendirmesini kaydeder.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PostMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<TrainerAssessmentCoefficients> updateTrainerAssessmentCoefficients(@RequestBody UpdateTrainerAssessmentCoefficientsRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentCoefficientsService.updateTrainerAssessmentCoefficients(dto));
    }

    @Operation(summary = "Eğitmen değerlendirme puan katsayılarını silme işlemi",
            description = "Belirtilen eğitmen değerlendirme puan katsayılarının güncelleme isteği DTO'su kullanılarak bir eğitmen değerlendirme katsayısını siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PostMapping(DELETE)
    @CrossOrigin("*")
    public ResponseEntity<TrainerAssessmentCoefficients> deleteTrainerAssessmentCoefficients(@RequestBody String trainerAssessmentCoefficientsId){
        return ResponseEntity.ok(trainerAssessmentCoefficientsService.deleteTrainerAssessmentCoefficients(trainerAssessmentCoefficientsId));
    }
}