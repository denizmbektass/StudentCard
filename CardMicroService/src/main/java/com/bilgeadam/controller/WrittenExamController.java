package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveWrittenExamRequestDto;
import com.bilgeadam.dto.request.UpdateWrittenExamRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.repository.entity.WrittenExam;
import com.bilgeadam.service.WrittenExamService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(WRITTEN_EXAM)
@RequiredArgsConstructor
public class WrittenExamController {
    private final WrittenExamService writtenExamService;

    @Operation(summary = "Ogrenci secme yazılı sınavı icin puan hesaplama islemi",
            description = "Verilen doğru cevap sayısını kullanarak yazılı sınav puanını hesaplar.")
    @GetMapping(WRITTEN_EXAM_SCORE)
    @CrossOrigin("*")
    public ResponseEntity<Double> calculateWrittenExamScore(@PathVariable int correctAnswers){
        return ResponseEntity.ok(writtenExamService.calculateWrittenExamScore(correctAnswers));
    }

    @Operation(summary = "Ogrenci secme yazılı sınav puanını kaydetme islemi",
            description = "Yazılı sınav puanını kaydeder.")
    @PostMapping(SAVE_WRITTEN_EXAM)
    @CrossOrigin("*")
    public ResponseEntity<WrittenExam> saveWrittenExam(@RequestBody @Valid SaveWrittenExamRequestDto dto){
        return ResponseEntity.ok(writtenExamService.saveWrittenExam(dto));
    }

    @Operation(summary = "Yazılı sınav puanını görüntüleme işlemi",
            description = "Yazılı sınav puanını studentId üzerinden çekip görüntüler.")
    @GetMapping(GET_WRITTEN_EXAM)
    @CrossOrigin("*")
    public ResponseEntity<WrittenExam> getWrittenExamByStudentId(@PathVariable String studentId){
        return ResponseEntity.ok(writtenExamService.getWrittenExamByStudentId(studentId));
    }

    @Operation(summary = "Yazılı sınav güncelleme işlemi",
            description = "Belirtilen Dto ile yazılı sınavı günceller.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateWrittenExam(@RequestBody UpdateWrittenExamRequestDto dto){
        return ResponseEntity.ok(writtenExamService.updateWrittenExam(dto));
    }

}
