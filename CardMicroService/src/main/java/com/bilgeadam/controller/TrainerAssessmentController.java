package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TokenRequestDto;
import com.bilgeadam.dto.request.SaveTrainerAssessmentRequestDto;
import com.bilgeadam.dto.request.UpdateTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.DeleteTrainerAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
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

    @Operation(summary = "Eğitmen değerlendirme silme işlemi",
            description = "Belirtilen eğitmen değerlendirme kimliği kullanılarak bir eğitmen değerlendirmesini siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @DeleteMapping(DELETE)
    @CrossOrigin("*")
    public ResponseEntity<DeleteTrainerAssessmentResponseDto> deleteTrainerAssessment(@RequestParam String id){
        return ResponseEntity.ok(trainerAssessmentService.deleteTrainerAssessment(id));
    }

    @Operation(summary = "Etkin eğitmen değerlendirmelerini getirme işlemi",
            description = "Etkin olan tüm eğitmen değerlendirmelerini getirir.")
    @PostMapping(FIND_ALL_ACTIVE_TRAINER_ASSESSMENT)
    @CrossOrigin("*")
    public ResponseEntity<List<TrainerAssessment>> findAllTrainerAssessmentActive(@RequestBody TokenRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentService.findAllTrainerAssessment(dto));
    }
}
