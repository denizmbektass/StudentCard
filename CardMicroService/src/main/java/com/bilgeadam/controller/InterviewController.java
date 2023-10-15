package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateInterviewRequestDto;

import com.bilgeadam.dto.request.TokenRequestDto;
import com.bilgeadam.dto.request.UpdateInterviewRequestDto;
import com.bilgeadam.dto.response.CreateInterviewResponseDto;
import com.bilgeadam.dto.response.DeleteInterviewResponseDto;
import com.bilgeadam.dto.response.UpdateInterviewResponseDto;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERVIEW)
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @Operation(summary = "Mülakat oluşturma işlemi",
            description = "Belirtilen oluşturma isteği DTO'su kullanılarak bir mülakat oluşturur.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','WORKSHOP_TEAM')")
    @PostMapping(CREATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<CreateInterviewResponseDto> createInterview(@RequestBody CreateInterviewRequestDto dto){
        return ResponseEntity.ok(interviewService.createInterview(dto));
    }

    @Operation(summary = "Tüm mülakatları alma işlemi",
            description = "Belirtilen token kullanılarak tüm mülakatları alır.")
    @PostMapping(FIND_ALL_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<List<Interview>> findAllInterview(@RequestBody TokenRequestDto dto){
        return ResponseEntity.ok(interviewService.findAllInterviews(dto));
    }

    @Operation(summary = "Mülakat güncelleme işlemi",
            description = "Belirtilen güncelleme isteği DTO'su kullanılarak bir mülakatı günceller.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','WORKSHOP_TEAM')")
    @PostMapping(UPDATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<UpdateInterviewResponseDto> updateInterview(@RequestBody UpdateInterviewRequestDto dto){
        return ResponseEntity.ok(interviewService.updateInterview(dto));
    }

    @Operation(summary = "Mülakat silme işlemi",
            description = "Belirtilen mülakat ID'sine sahip mülakatı siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','WORKSHOP_TEAM')")
    @DeleteMapping(DELETE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<DeleteInterviewResponseDto> deleteInterview(@RequestParam String id){
        return ResponseEntity.ok(interviewService.deleteInterview(id));
    }
}
