package com.bilgeadam.controller;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(CARD)
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @Operation(summary = "Öğrenciye ait kartı alma işlemi",
            description = "Belirtilen token kullanılarak öğrenciye ait kartı alır.")
    @CrossOrigin("*")
    @GetMapping("/get-card/{token}")
    public ResponseEntity<CardResponseDto> getCardByStudent(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getCardByStudent(token));
    }


    @Operation(summary = "Öğrenci için kart parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak öğrenci için kart parametrelerini alır.")
    @CrossOrigin("*")
    @GetMapping("/get-card-parameter-for-student/{token}")
    public ResponseEntity<Map<String, Integer>> getCardParameterForStudent(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getCardParameterForStudent(token));
    }

    @CrossOrigin("*")
    @GetMapping("/get-transcript/{token}")
    public ResponseEntity<TranscriptResponseDto> getTranscriptByStudent(@PathVariable String token) {
        System.out.println("token burada    "+token);
        return ResponseEntity.ok(cardService.getTranscriptByStudent(token));
    }

    @Operation(summary = "Eğitim kartı için parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak eğitim için kart bilgilerini almak.")
    @GetMapping("/get-education-score-details/{token}")
    @CrossOrigin("*")
    public ResponseEntity<EducationScoreDetailsDto> getEducationDetails(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getEducationDetails(token));
    }

    @Operation(summary = "Ögrenci Seçme kartı için parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak öğrenci için kart bilgilerini almak.")
    @GetMapping("/get-student-choice-score-details/{token}")
    @CrossOrigin("*")
    public ResponseEntity<StudentChoiceResponseDto> getStudentChoiceDetails(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getStudentChoiceDetails(token));
    }
    @Operation(summary = "İstihdam kartı için parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak istihdam için kart bilgilerini almak.")
    @GetMapping("/get-employment-score-details/{token}")
    @CrossOrigin("*")
    public ResponseEntity<EmploymentScoreDetailsDto> getEmploymentDetailsDetails(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getEmploymentDetails(token));
    }
    @Operation(summary = "Staj Başarı kartı için parametrelerini alma işlemi",
            description = "Belirtilen token kullanılarak Staj Başarı  için kart bilgilerini almak.")
    @GetMapping("/get-internship-success-score-details/{token}")
    @CrossOrigin("*")
    public ResponseEntity<InternshipSuccessResponseDto> getInternshipSuccessDetails(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getInternshipSuccess(token));
    }

    @Operation(summary = "Transkript bilgilerini pdf'e ekleme işlemi",
            description = "Belirtilen token kullanılarak Transkript  için bilgileri bilgilerini alma ve pdf oluşturma işlemi.")
    @GetMapping("/get-create-pdf/{token}")
    @CrossOrigin("*")
    public void createPdf(HttpServletResponse response, @RequestParam String token) throws JRException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + "pdf";
        response.setHeader(headerKey,headerValue);
        cardService.getCreatePdf(response, token);
    }
}
