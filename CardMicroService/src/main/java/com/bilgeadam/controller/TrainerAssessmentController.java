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

    private final TrainerAssessmentService trainerAssessmentService;

    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<TrainerAssessmentSaveResponseDto> saveTrainerAssessment(@RequestBody TrainerAssessmentSaveRequestDto dto){
        return ResponseEntity.ok(trainerAssessmentService.saveTrainerAssessment(dto));
    }
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<UpdateTrainerAssessmentResponseDto> updateTrainerAssessment(@RequestBody UpdateTrainerAssessmentRequestDto dto,String id){
        return ResponseEntity.ok(trainerAssessmentService.updateTrainerAssessment(dto,id));
    }
    @DeleteMapping(DELETE)
    @CrossOrigin("*")
    public ResponseEntity<String> deleteTrainerAssessment(String id){
        return ResponseEntity.ok(trainerAssessmentService.deleteTrainerAssessment(id));
    }
    @GetMapping(FIND_ALL_ACTIVE_TRAINER_ASSESSMENT)
    @CrossOrigin("*")
    public ResponseEntity<List<TrainerAssessment>> findAllTrainerAssessmentActive(String studentId){
        return ResponseEntity.ok(trainerAssessmentService.findAllTrainerAssessment(studentId));
    }
    @GetMapping(FIND_ALL)
    @CrossOrigin("*")
    public ResponseEntity<List<TrainerAssessment>> findAllTrainerAssessment(){
        return ResponseEntity.ok(trainerAssessmentService.findAll());
    }
}
