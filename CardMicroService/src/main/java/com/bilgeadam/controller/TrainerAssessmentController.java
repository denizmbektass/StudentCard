package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.request.UpdateTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.service.TrainerAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TRAINER_ASSESSMENT)
@RequiredArgsConstructor
public class TrainerAssessmentController {

    private final TrainerAssessmentService trainerAssesmentService;

    @PostMapping(SAVE)
    public ResponseEntity<TrainerAssessmentSaveResponseDto> saveTrainerAssesment(TrainerAssessmentSaveRequestDto dto){
        return ResponseEntity.ok(trainerAssesmentService.saveTrainerAssessment(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<UpdateTrainerAssessmentResponseDto> updateTrainerAssesment(UpdateTrainerAssessmentRequestDto dto,String id){
        return ResponseEntity.ok(trainerAssesmentService.updateTrainerAssessment(dto,id));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<String> deleteTrainerAssesment(String id){
        return ResponseEntity.ok(trainerAssesmentService.deleteTrainerAssessment(id));
    }
    @GetMapping(FIND_ALL_ACTIVE_TRAINER_ASSESSMENT)
    public ResponseEntity<List<TrainerAssessment>> findAllTrainerAssesment(){
        return ResponseEntity.ok(trainerAssesmentService.findAllTrainerAssessment());
    }
}
