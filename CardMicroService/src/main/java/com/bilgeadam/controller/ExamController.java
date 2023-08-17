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
import java.util.Set;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(EXAM)
@RequiredArgsConstructor
public class ExamController {
    private  final ExamService examService;
    @CrossOrigin("*")
    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createExam(@RequestBody @Valid CreateExamRequestDto dto){
        return  ResponseEntity.ok(examService.createExam(dto));
    }

    @GetMapping(FIND_ALL+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<ExamResponseDto>> findAllExams(@PathVariable String token){
        return  ResponseEntity.ok(examService.findAllExams(token));
    }


    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateExam(@RequestBody UpdateExamRequestDto dto){
        return  ResponseEntity.ok(examService.updateExam(dto));
    }



    @DeleteMapping(DELETE+"/{examId}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteExam(@PathVariable String examId){
        examService.deleteExam(examId);
        return ResponseEntity.ok(new MessageResponse("Sınav başarıyla silindi.."));
    }

    @GetMapping(FIND_ALL+"/title/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Set<String>> getAllTitles(@PathVariable String token){

        return ResponseEntity.ok(examService.getAllTitles(token));
}

}
