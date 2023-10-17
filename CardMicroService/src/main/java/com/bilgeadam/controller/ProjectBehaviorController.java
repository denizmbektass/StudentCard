package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreatProjecrBehaviorScoreRequestDto;
import com.bilgeadam.dto.request.UpdateExamRequestDto;
import com.bilgeadam.dto.request.UpdateProjectBehaviorRequestDto;
import com.bilgeadam.dto.response.CreateProjectBehaviorScoreResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.repository.entity.ProjectBehavior;
import com.bilgeadam.service.ProjectBehaviorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;
@RequiredArgsConstructor
@RestController
@RequestMapping(PROJECTBEHAVIOR)
public class ProjectBehaviorController {

    private final ProjectBehaviorService projectBehaviorService;

    @Operation(summary = "Davranış puanlama işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<CreateProjectBehaviorScoreResponseDto> createProjectBehaviorScore(@RequestBody CreatProjecrBehaviorScoreRequestDto dto){
        return ResponseEntity.ok(projectBehaviorService.createProjectBehaviorScore(dto));
    }

    @Operation(summary = "Davranış puanlarını güncelleme işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateExam(@RequestBody UpdateProjectBehaviorRequestDto dto){
        return  ResponseEntity.ok(projectBehaviorService.updateProjectBehavior(dto));
    }
    @Operation(summary = "Davranış puanı silme işlemi",
            description = "Belirtilen davranış puanlama kimliği kullanılarak bir davranış puanlarını siler.")
    @DeleteMapping(DELETE+"/{projectBehaviorId}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> deleteProjectBehavior(@PathVariable String projectBehaviorId){
        return ResponseEntity.ok(projectBehaviorService.deleteProjectBehavior(projectBehaviorId));
    }
}
