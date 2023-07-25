package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateExamRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateExamRequestDto;
import com.bilgeadam.dto.response.ExamResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(EXAM)
@RequiredArgsConstructor
public class ExamController {
    private  final ExamService examService;

    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createExam(@RequestBody @Valid CreateExamRequestDto dto){
        return  ResponseEntity.ok(examService.createExam(dto));
    }
    @PostMapping(FIND_ALL)
    public ResponseEntity<List<ExamResponseDto>> findAllExams(@RequestBody @Valid FindByStudentIdRequestDto dto){
        return  ResponseEntity.ok(examService.findAllExams(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<MessageResponse> updateExam(@RequestBody UpdateExamRequestDto dto){
        return  ResponseEntity.ok(examService.updateExam(dto));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<MessageResponse> deleteExam(@RequestParam String examId){
        return  ResponseEntity.ok(examService.deleteExam(examId));
    }

}
