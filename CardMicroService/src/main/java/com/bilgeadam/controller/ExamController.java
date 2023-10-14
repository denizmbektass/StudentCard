package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateExamRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateExamRequestDto;
import com.bilgeadam.dto.response.AverageExamResponseDto;
import com.bilgeadam.dto.response.ExamResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Sınav oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak bir sınav oluşturur.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @CrossOrigin("*")
    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createExam(@RequestBody @Valid CreateExamRequestDto dto){
        return  ResponseEntity.ok(examService.createExam(dto));
    }


    @Operation(summary = "Tüm sınavları alma işlemi",
            description = "Belirtilen token kullanılarak tüm sınavları alır.")
    @GetMapping(FIND_ALL+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<ExamResponseDto>> findAllExams(@PathVariable String token){
        return  ResponseEntity.ok(examService.findAllExams(token));
    }


    @Operation(summary = "Sınav güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak bir sınavı günceller.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateExam(@RequestBody UpdateExamRequestDto dto){
        return  ResponseEntity.ok(examService.updateExam(dto));
    }


    @Operation(summary = "Sınav silme işlemi",
            description = "Belirtilen sınav kimliği kullanılarak bir sınavı siler.")
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @DeleteMapping(DELETE+"/{examId}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteExam(@PathVariable String examId){
        examService.deleteExam(examId);
        return ResponseEntity.ok(new MessageResponse("Sınav başarıyla silindi.."));
    }


    @Operation(summary = "Tüm başlıkları alma işlemi",
            description = "Belirtilen token kullanılarak tüm sınav başlıklarını alır.")
    @GetMapping(FIND_ALL+"/title/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Set<String>> getAllTitles(@PathVariable String token){

        return ResponseEntity.ok(examService.getAllTitles(token));
}

    @Operation(summary = "Sınavlar için ortalama hesaplama işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @GetMapping(AVERAGE)
    @CrossOrigin("*")
    public ResponseEntity<AverageExamResponseDto> getAverageExam(@PathVariable String studentId){
        return ResponseEntity.ok(examService.averageExam(studentId));
    }

}
