package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.request.UpdateProjectRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.dto.response.StudentProjectListResponseDto;
import com.bilgeadam.dto.response.UpdateProjectResponseDto;
import com.bilgeadam.repository.enums.EProjectType;
import com.bilgeadam.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    @CrossOrigin
    @GetMapping("/show-project-type")
    public ResponseEntity<List<EProjectType>> showProjectsType(){
        return ResponseEntity.ok(projectService.showProjectsType());
    }

    @CrossOrigin
    @GetMapping("/show-project-type/{studentToken}")
    public ResponseEntity<List<StudentProjectListResponseDto>> showStudentProjectList(@PathVariable String studentToken){
        return ResponseEntity.ok(projectService.showStudentProjectList(studentToken));
    }

    @CrossOrigin
    @PutMapping("/delete-student-project/{projectId}")
    public ResponseEntity<Boolean> deleteStudentProject(@PathVariable String projectId){
        return ResponseEntity.ok(projectService.deleteStudentProject(projectId));
    }
    @CrossOrigin
    @PutMapping("/update-student-project")
    public ResponseEntity<UpdateProjectResponseDto> updateStudentProject(@RequestBody UpdateProjectRequestDto dto){
        return ResponseEntity.ok(projectService.updateStudentProject(dto));
    }


}
