package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(PROJECT)
public class ProjectController {
    private final ProjectService projectService;

    @CrossOrigin
    @PostMapping(CREATE_PROJECT_SCORE)
    public ResponseEntity<CreateProjectScoreResponseDto> createProjectScore (@RequestBody CreateProjectScoreRequestDto dto){
        return ResponseEntity.ok(projectService.createProjectScore(dto));
    }
}
