package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreatProjectBehaviorScoreRequestDto;

import com.bilgeadam.dto.request.UpdateProjectBehaviorRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.service.ProjectBehaviorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;
@RequiredArgsConstructor
@RestController
@RequestMapping(PROJECT_BEHAVIOR)
public class ProjectBehaviorController {

    private final ProjectBehaviorService projectBehaviorService;

    @Operation(summary = "Davranış puanlama işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<CreateProjectBehaviorScoreResponseDto> createProjectBehaviorScore(@RequestBody @Valid CreatProjectBehaviorScoreRequestDto dto){
        return ResponseEntity.ok(projectBehaviorService.createProjectBehaviorScore(dto));
    }

    @Operation(summary = "Davranış puanlarını güncelleme işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateProjectBehavior(@RequestBody @Valid UpdateProjectBehaviorRequestDto dto){
        return  ResponseEntity.ok(projectBehaviorService.updateProjectBehavior(dto));
    }
    @Operation(summary = "Davranış puanı silme işlemi",
            description = "Belirtilen davranış puanlama kimliği kullanılarak bir davranış puanlarını siler.")
    @DeleteMapping(DELETE+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> deleteProjectBehavior(@PathVariable String token){
        return ResponseEntity.ok(projectBehaviorService.deleteProjectBehavior(token));
    }

    @Operation(summary = "Davranış puanlarını  alma işlemi",
            description = "Belirtilen token kullanılarak proje davranış puanlarını getirir.")
    @GetMapping(FIND_PROJECT_BEHAVIOR+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<GetProjectBehaviorResponseDto> findProjectBehavior(@PathVariable String token){
        return  ResponseEntity.ok(projectBehaviorService.findProjectBehavior(token));
    }
}
