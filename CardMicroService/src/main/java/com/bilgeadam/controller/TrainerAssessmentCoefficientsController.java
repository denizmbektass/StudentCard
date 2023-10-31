package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveTrainerAssessmentRequestDto;
import com.bilgeadam.dto.request.UpdateTrainerAssessmentCoefficientsRequestDto;
import com.bilgeadam.dto.response.GetTrainerAssessmentCoefficientsResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import com.bilgeadam.service.TrainerAssessmentCoefficientsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TRAINER_ASSESSMENT_COEFFICIENTS)
@RequiredArgsConstructor
public class TrainerAssessmentCoefficientsController {

    private final TrainerAssessmentCoefficientsService trainerAssessmentCoefficientsService;

    @Operation(summary = "Eğitmen görüş  oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak Eğitmen görüş oluşturur.")
    @CrossOrigin("*")
    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createTrainerAssessmentCoefficients(@RequestBody @Valid SaveTrainerAssessmentRequestDto dto){
        return  ResponseEntity.ok(trainerAssessmentCoefficientsService.createTrainerAssessmentCoefficients(dto));
    }
    @Operation(summary = "Eğitmen değerlendirme puan katsayılarının güncelleme ve kaydedilme işlemi",
            description = "Belirtilen eğitmen değerlendirme puan katsayılarının güncelleme isteği DTO'su kullanılarak bir eğitmen değerlendirmesini kaydeder.")
    @PutMapping (UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<TrainerAssessmentCoefficients> updateTrainerAssessmentCoefficients(@RequestBody @Valid UpdateTrainerAssessmentCoefficientsRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentCoefficientsService.updateTrainerAssessmentCoefficients(dto));
    }

    @Operation(summary = "Eğitmen değerlendirme puan katsayılarını silme işlemi",
            description = "Belirtilen eğitmen değerlendirme puan katsayılarının güncelleme isteği DTO'su kullanılarak bir eğitmen değerlendirme katsayısını siler.")
    @DeleteMapping(DELETE+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteTrainerAssessmentCoefficients(@PathVariable String token){
        return ResponseEntity.ok(trainerAssessmentCoefficientsService.deleteTrainerAssessmentCoefficients(token));
    }

    @Operation(summary = "Eğitmen görüşünü  alma işlemi",
            description = "Belirtilen token kullanılarak Eğitmen görüşünü getirir.")
    @GetMapping(FIND_ALL+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<GetTrainerAssessmentCoefficientsResponseDto> findTrainerAssessmentCoefficients(@PathVariable String token){
        return  ResponseEntity.ok(trainerAssessmentCoefficientsService.getTrainerAssessmentCoefficientsResponseDto(token));
    }
}