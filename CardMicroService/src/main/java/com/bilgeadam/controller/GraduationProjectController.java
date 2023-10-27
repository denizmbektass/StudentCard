package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateGraduationProjectRequestDto;
import com.bilgeadam.dto.request.UpdateGraduationProjectRequestDto;
import com.bilgeadam.dto.response.GetGraduationProjectResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.service.GraduationProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(GRADUATION_PROJECT)
@RequiredArgsConstructor
public class GraduationProjectController {
    private final GraduationProjectService graduationProjectService;

    @Operation(summary = "Bitirme Projesi oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak Bitirme projesi oluşturur.")
    @CrossOrigin("*")
    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createGraduationProject(@RequestBody @Valid CreateGraduationProjectRequestDto dto){
        return  ResponseEntity.ok(graduationProjectService.createGradutainProject(dto));
    }
    @Operation(summary = "Öğrencinin Bitirme projesini  alma işlemi",
            description = "Belirtilen token kullanılarak Bitirme projesini getirir.")
    @GetMapping(FIND_GRADUATION_PROJECT+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<GetGraduationProjectResponseDto> findGraduationProject(@PathVariable String token){
        return  ResponseEntity.ok(graduationProjectService.findGraduationProject(token));
    }

    @Operation(summary = "Öğrencinin Bitirme Projesi Güncelleme işlemi",
            description = "Belirtilen token kullanılarak Bitirme projesini günceller.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateProject(@RequestBody @Valid UpdateGraduationProjectRequestDto dto){
        return  ResponseEntity.ok(graduationProjectService.updateProject(dto));
    }
    @Operation(summary = "Öğrencinin Bitirme Projesi silme işlemi",
            description = "Belirtilen token kullanılarak Bitirme projesini silinir.")
    @DeleteMapping (DELETE+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable String token){
        return  ResponseEntity.ok(graduationProjectService.deleteProject(token));
    }

}
