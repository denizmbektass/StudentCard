package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateGraduationProjectRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.GraduationProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(GRADUATIONPROJECT)
@RequiredArgsConstructor
public class GraduationProjectController {
    private final GraduationProjectService graduationProjectService;

    @Operation(summary = "Bitirme Projesi oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak bir sınav oluşturur.")
    @CrossOrigin("*")
    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createGraduationProject(@RequestBody @Valid CreateGraduationProjectRequestDto dto){
        return  ResponseEntity.ok(graduationProjectService.createGradutainProject(dto));
    }
}
