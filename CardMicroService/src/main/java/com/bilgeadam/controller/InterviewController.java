package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateInterviewRequestDto;
import com.bilgeadam.dto.response.CreateInterviewResponseDto;
import com.bilgeadam.dto.response.DeleteInterviewResponseDto;
import com.bilgeadam.dto.response.UpdateInterviewResponseDto;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERVIEW)
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @PostMapping(CREATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<CreateInterviewResponseDto> createInterview(@RequestBody CreateInterviewRequestDto dto){
        return ResponseEntity.ok(interviewService.createInterview(dto));
    }
    @GetMapping(FIND_ALL_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<List<Interview>> findAllInterview(){
        return ResponseEntity.ok(interviewService.findAll());
    }
    @PostMapping(UPDATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<UpdateInterviewResponseDto> updateInterview(@RequestBody UpdateInterviewRequestDto dto){
        return ResponseEntity.ok(interviewService.updateInterview(dto));
    }
    @DeleteMapping(DELETE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<DeleteInterviewResponseDto> deleteInterview(@RequestParam String id){
        return ResponseEntity.ok(interviewService.deleteInterview(id));
    }
}
