package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateTechnicalInterviewRequestDto;
import com.bilgeadam.dto.request.SaveTechnicalInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateTechnicalInterviewRequestDto;
import com.bilgeadam.dto.response.GetTechnicalInterviewResponseDto;
import com.bilgeadam.service.TechnicalInterviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TECHNICAL_INTERVIEW)
@RequiredArgsConstructor
public class TechnicalInterviewController {

    private final TechnicalInterviewService technicalInterviewService;

    @Operation(summary = "Teknik mülakat kaydetme işlemi",
            description = "Öğrencinin teknik mülakat verilerini kaydetmek için kullanılır. İlgili verileri içeren SaveTechnicalInterviewRequestDto nesnesi, " +
                    "HTTP POST isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveTechnicalInterview(@RequestBody @Valid SaveTechnicalInterviewRequestDto dto) {
        return ResponseEntity.ok(technicalInterviewService.saveTechnicalInterview(dto));
    }

    @Operation(summary = "Teknik mülakat puanlarını getirir",
            description = "Öğrencinin teknik mülakat verilerini almak için kullanılır. İlgili öğrencinin kimliği," +
                    " URL'de yer alan 'studentId' parametresi ile belirtilir.")
    @GetMapping(GET_TECHNICAL_INTERVIEW + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetTechnicalInterviewResponseDto> getTechnicalInterview(@PathVariable String studentId) {
        return ResponseEntity.ok(technicalInterviewService.getTechnicalInterview(studentId));
    }

    @Operation(summary = "Teknik mülakatı günceller",
            description = "Öğrencinin teknik mülakat verilerini güncellemek için kullanılır. İlgili verileri içeren UpdateTechnicalInterviewRequestDto nesnesi, " +
                    "HTTP PUT isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateTechnicalInterview(@RequestBody @Valid UpdateTechnicalInterviewRequestDto dto) {
        return ResponseEntity.ok(technicalInterviewService.updateTechnicalInterview(dto));
    }

    @Operation(summary = "Teknik mülakat sayısını getirir.",
            description = "Öğrencinin teknik mülakat sayısını almak için kullanılır. " +
                    "Öğrencinin kimliği, URL'de yer alan 'studentId' parametresi ile belirtilir.")
    @GetMapping(GET_TECHNICAL_INTERVIEW_NUMBER + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getTechnicalInterviewNumber(@PathVariable String studentId) {
        return ResponseEntity.ok(technicalInterviewService.getTechnicalInterviewNumber(studentId));
    }

    @Operation(summary = "Teknik mülakat ortalama puanını getirir.",
            description = "Öğrencinin teknik mülakatlarının ortalama puanını almak için kullanılır." +
                    " Öğrencinin kimliği, URL'de yer alan 'studentId' parametresi ile belirtilir.")
    @GetMapping(GET_TECHNICAL_INTERVIEW_AVERAGE_POINT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getTechnicalInterviewAveragePoint(@PathVariable String studentId) {
        return ResponseEntity.ok(technicalInterviewService.getTechnicalInterviewAveragePoint(studentId));
    }

    @Operation(summary = "Teknik mülakatı siler",
            description = "Öğrencinin teknik mülakat verilerini silmek için kullanılır. İlgili teknik mülakat ID'si, " +
                    "HTTP DELETE isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deleteTechnicalInterview(@PathVariable String technicalInterviewId) {
        boolean result = technicalInterviewService.deleteTechnicalInterview(technicalInterviewId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Teknik mülakat oluşturur",
            description = "Öğrencinin teknik mülakat verilerini oluşturmak için kullanılır. İlgili verileri içeren CreateTechnicalInterviewRequestDto nesnesi, " +
                    "HTTP POST isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createTechnicalInterview(@RequestBody CreateTechnicalInterviewRequestDto dto) {
        boolean result = technicalInterviewService.createTechnicalInterview(dto);
        return ResponseEntity.ok(result);


    }
}