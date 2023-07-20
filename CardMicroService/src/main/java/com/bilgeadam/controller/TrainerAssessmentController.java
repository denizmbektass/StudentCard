package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.request.UpdateTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.service.TrainerAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TRAINERASSESSMENT)
@RequiredArgsConstructor
public class TrainerAssessmentController {

    private final TrainerAssessmentService trainerAssesmentService;

    @PostMapping(SAVE)
    public ResponseEntity<TrainerAssessmentSaveResponseDto> saveTrainerAssesment(TrainerAssessmentSaveRequestDto dto){
        return ResponseEntity.ok(trainerAssesmentService.saveTrainerAssessment(dto));
    }
    @PostMapping(UPDATE)
    public ResponseEntity<UpdateTrainerAssessmentResponseDto> updateTrainerAssesment(UpdateTrainerAssessmentRequestDto dto,String id){
        return ResponseEntity.ok(trainerAssesmentService.updateTrainerAssessment(dto,id));
    }
    @PostMapping(DELETE)
    public ResponseEntity<String> deleteTrainerAssesment(String id){
        return ResponseEntity.ok(trainerAssesmentService.deleteTrainerAssessment(id));
    }
}
