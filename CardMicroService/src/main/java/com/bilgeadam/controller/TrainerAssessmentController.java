package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TokenRequestDto;
import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.request.TrainerAssessmentScoreCalculateRequestDto;
import com.bilgeadam.dto.request.UpdateTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.DeleteAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentScoreCalculateResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.service.TrainerAssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TRAINER_ASSESSMENT)
@RequiredArgsConstructor
public class TrainerAssessmentController {

    private final TrainerAssessmentService trainerAssessmentService;

    @Operation(summary = "Eğitmen değerlendirme puanını hesaplama işlemi",
            description = "Belirtilen eğitmen değerlendirme puanı hesaplama isteği DTO'su kullanılarak bir eğitmen değerlendirme puanını hesaplar.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PostMapping(TRAINER_ASSESSMENT)
    @CrossOrigin("*")
    public ResponseEntity<TrainerAssessmentScoreCalculateResponseDto> calculateTotalTrainerAssessmentScore(@RequestBody TrainerAssessmentScoreCalculateRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentService.calculateTrainerAssessmentScore(dto));
    }
    @Operation(summary = "Eğitmen değerlendirme kaydetme işlemi",
            description = "Belirtilen eğitmen değerlendirme kaydetme isteği DTO'su kullanılarak bir eğitmen değerlendirmesini kaydeder.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<TrainerAssessmentSaveResponseDto> saveTrainerAssessment(@RequestBody TrainerAssessmentSaveRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentService.saveTrainerAssessment(dto));
    }

    @Operation(summary = "Eğitmen değerlendirme güncelleme işlemi",
            description = "Belirtilen eğitmen değerlendirme güncelleme isteği DTO'su kullanılarak bir eğitmen değerlendirmesini günceller.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PostMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<UpdateTrainerAssessmentResponseDto> updateTrainerAssessment(@RequestBody UpdateTrainerAssessmentRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentService.updateTrainerAssessment(dto));
    }

    @Operation(summary = "Eğitmen değerlendirme silme işlemi",
            description = "Belirtilen eğitmen değerlendirme kimliği kullanılarak bir eğitmen değerlendirmesini siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @DeleteMapping(DELETE)
    @CrossOrigin("*")
    public ResponseEntity<DeleteAssessmentResponseDto> deleteTrainerAssessment(@RequestParam String id){
        return ResponseEntity.ok(trainerAssessmentService.deleteTrainerAssessment(id));
    }

    @Operation(summary = "Etkin eğitmen değerlendirmelerini getirme işlemi",
            description = "Etkin olan tüm eğitmen değerlendirmelerini getirir.")
    @PostMapping(FIND_ALL_ACTIVE_TRAINER_ASSESSMENT)
    @CrossOrigin("*")
    public ResponseEntity<List<TrainerAssessment>> findAllTrainerAssessmentActive(@RequestBody TokenRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentService.findAllTrainerAssessment(dto));
    }


//    @GetMapping(FIND_ALL)
//    @CrossOrigin("*")
//    public ResponseEntity<List<TrainerAssessment>> findAllTrainerAssessment(){
//        return ResponseEntity.ok(trainerAssessmentService.findAll());
//    }
}
