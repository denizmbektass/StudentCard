package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.request.CreateProjectTypeRequestDto;
import com.bilgeadam.dto.request.UpdateProjectRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.dto.response.StudentProjectListResponseDto;
import com.bilgeadam.dto.response.UpdateProjectResponseDto;
import com.bilgeadam.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(PROJECT)
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Proje puanı oluşturma işlemi",
            description = "Belirtilen proje puanı oluşturma isteği DTO'su kullanılarak bir proje puanı oluşturur.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @CrossOrigin
    @PostMapping(CREATE_PROJECT_SCORE)
    public ResponseEntity<CreateProjectScoreResponseDto> createProjectScore (@RequestBody CreateProjectScoreRequestDto dto){
        return ResponseEntity.ok(projectService.createProjectScore(dto));
    }

    @Operation(summary = "Proje türlerini gösterme işlemi",
            description = "Mevcut proje türlerini listeleyerek döner.")
    @CrossOrigin
    @GetMapping("/show-project-type")
    public ResponseEntity<List<String>> showProjectsType(){
        return ResponseEntity.ok(projectService.showProjectsType());
    }


    @Operation(summary = "Öğrencinin proje türlerini gösterme işlemi",
            description = "Belirtilen öğrencinin proje türlerini listeleyerek döner.")
    @CrossOrigin
    @GetMapping("/show-project-type/{studentToken}")
    public ResponseEntity<List<StudentProjectListResponseDto>> showStudentProjectList(@PathVariable String studentToken){
        return ResponseEntity.ok(projectService.showStudentProjectList(studentToken));
    }

    @Operation(summary = "Öğrencinin projesini silme işlemi",
            description = "Belirtilen proje ID'sine sahip öğrencinin projesini siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @CrossOrigin
    @PutMapping("/delete-student-project/{projectId}")
    public ResponseEntity<Boolean> deleteStudentProject(@PathVariable String projectId){
        return ResponseEntity.ok(projectService.deleteStudentProject(projectId));
    }

    @Operation(summary = "Öğrencinin proje güncelleme işlemi",
            description = "Belirtilen güncelleme isteği DTO'su kullanılarak bir öğrencinin projesini günceller.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @CrossOrigin
    @PutMapping("/update-student-project")
    public ResponseEntity<UpdateProjectResponseDto> updateStudentProject(@RequestBody UpdateProjectRequestDto dto){
        return ResponseEntity.ok(projectService.updateStudentProject(dto));
    }

    @Operation(summary = "Proje türü oluşturma işlemi",
            description = "Belirtilen proje türü oluşturma isteği DTO'su kullanılarak bir proje türü oluşturur.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @CrossOrigin
    @PostMapping("/create-project-type")
    public ResponseEntity<String> createProjectType (@RequestBody CreateProjectTypeRequestDto dto){
        return ResponseEntity.ok(projectService.createProjectType(dto));
    }


    @Operation(summary = "Proje türü silme işlemi",
            description = "Belirtilen proje türü ID'sine sahip proje türünü siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @CrossOrigin
    @PutMapping("delete-project-type/{projectTypeId}")
    public ResponseEntity<Boolean> deleteProjectType(@PathVariable String projectTypeId){
        return ResponseEntity.ok(projectService.deleteProjectType(projectTypeId));
    }
}
