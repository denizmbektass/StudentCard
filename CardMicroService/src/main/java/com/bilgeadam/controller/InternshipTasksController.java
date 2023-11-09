package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveInternshipTaskRequestDto;
import com.bilgeadam.dto.request.SaveTeamworkRequestDto;
import com.bilgeadam.dto.request.UpdateTeamworkRequestDto;
import com.bilgeadam.dto.response.GetInternshipTaskResponseDto;
import com.bilgeadam.dto.response.GetTeamworkResponseDto;
import com.bilgeadam.service.InternshipTasksService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERNSHIP_TASKS)
@RequiredArgsConstructor
public class InternshipTasksController {
    private final InternshipTasksService internshipTasksService;

    @Operation(summary = "Staj görevler kayıt", description = "Staj menüsündeki Görevler " +
            "sayfasında yapılan görevler kaydetme işlemidir.")
    @PostMapping(SAVE_INTERNSHIP_TASK)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveTeamwork(@Valid @RequestBody SaveInternshipTaskRequestDto dto) {
        System.out.println(dto);
        return ResponseEntity.ok(internshipTasksService.saveInternshipTask(dto));
    }

    @Operation(summary = "Staj Görev Çalışması sayfasında db'deki kariyer eğitimi kaydı sayısını döner",
            description = "Staj menüsünde Görev  sayfasında öğrencinin DB'de kayıtlı görev çalışması" +
                    "sayısını döner (0 ya da 1)")
    @GetMapping(GET_INTERNSHIP_TASK_COUNT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getInternshipTaskCount(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(internshipTasksService.getInternshipTaskCount(studentId));
    }

    @Operation(summary = "Ekip Çalışması sayfasında ekip çalışmasını günceller", description = "Staj menüsünde " +
            "Ekip Çalışması sayfasında öğrencinin daha önce girilmiş ekip çalışması varsa üstüne kaydeder")
    @PutMapping(UPDATE_INTERNSHIP)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateInternShipTask(@Valid @RequestBody SaveInternshipTaskRequestDto dto) {
        return ResponseEntity.ok(internshipTasksService.updateInternshipTask(dto));
    }

    @Operation(summary = "Staj Görevler sayfasında mevcut puanları getirir", description = "Staj menüsünde " +
            "Görevler başlığında öğrencinin önceden kaydedilmiş ekip çalışması puan değerleri varsa getirir")
    @GetMapping(GET_INTERNSHIP + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetInternshipTaskResponseDto> getInternShipTask(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(internshipTasksService.getInternshipTask(studentId));
    }

    @Operation(summary = "Staj Görevler sayfasında ortalama puanı getirir", description = "Staj menüsünde " +
            "Görevler sayfasında öğrencinin ekip çalışması başarı puan bilgisini döner")
    @GetMapping(GET_INTERNSHIP_SUCCESS_POINT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getInternShipTaskSuccessPoint(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(internshipTasksService.getInternShipTaskSuccessPoint(studentId));
    }

}
