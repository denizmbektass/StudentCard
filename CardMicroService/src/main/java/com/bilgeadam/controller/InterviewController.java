package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;

import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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

    @Operation(summary = "Aday mülakatı kaydetme işlemi", description = "Öğrenci seçme menüsündeki Aday Mülakatı" +
            " başlığından yapılan aday mülakatı kaydetme işlemidir.")
    @PostMapping(SAVE_CANDIDATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveCandidateInterview(@Valid @RequestBody SaveInterviewRequestDto dto){
        return ResponseEntity.ok(interviewService.saveCandidateInterview(dto));
    }

    @Operation(summary = "Aday mülakatı sayfasında mevcut puanları getirir", description = "Öğrenci seçme menüsünde " +
            "Aday Mülakatı başlığında öğrencinin daha önceden kaydedilen aday mülakatı puan değerleri varsa getirir")
    @GetMapping(GET_CANDIDATE_INTERVIEW+"/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetCandidateInterviewResponseDto> getCandidateInterview(@PathVariable @NotEmpty String studentId){
        return ResponseEntity.ok(interviewService.getCandidateInterview(studentId));
    }

    @Operation(summary = "Aday mülakatı sayfasında aday mülakatını günceller", description = "Öğrenci seçme" +
            " menüsünde Aday Mülakatı başlığında öğrencinin daha önce girilmiş mülakatı varsa üstüne kaydeder")
    @PutMapping(UPDATE_CANDIDATE_INTERVIEW)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateCandidateInterview(@Valid @RequestBody UpdateCandidateInterviewRequestDto dto){
        return ResponseEntity.ok(interviewService.updateCandidateInterview(dto));
    }

    @Operation(summary = "Aday mülakatı sayfasında aday mülakatı sayısını döner", description = "Öğrenci seçme " +
            "menüsünde Aday Mülakatı başlığında öğrencinin DB'de kayıtlı aday mülakatı sayısını döner (0 ya da 1)")
    @GetMapping(GET_CANDIDATE_INTERVIEW_NUMBER+"/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getCandidateInterviewNumber(@PathVariable @NotEmpty String studentId){
        return ResponseEntity.ok(interviewService.getCandidateInterviewNumber(studentId));
    }

    @Operation(summary = "Aday mülakatı sayfasında ortalama puanı döner", description = "Öğrenci seçme menüsünde " +
            "Aday Mülakatı başlığında öğrencinin ortalama aday mülakatı puan bilgisini döner")
    @GetMapping(GET_CANDIDATE_INTERVIEW_AVERAGE_POINT+"/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getCandidateInterviewAveragePoint(@PathVariable @NotEmpty String studentId){
        return ResponseEntity.ok(interviewService.getCandidateInterviewAveragePoint(studentId));
    }




}
