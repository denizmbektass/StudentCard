package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveCareerEducationRequestDto;
import com.bilgeadam.dto.request.UpdateCareerEducationRequestDto;
import com.bilgeadam.dto.response.GetCareerEducationResponseDto;
import com.bilgeadam.service.CareerEducationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CAREER_EDUCATION)
public class CareerEducationController {
    private final CareerEducationService careerEducationService;

    @Operation(summary = "Kariyer Eğitimi sayfasında db'deki kariyer eğitimi kaydı sayısını döner",
    description = "İstihdam menüsünde Kariyer eğitimi sayfasında öğrencinin DB'de kayıtlı kariyer eğitimi" +
            "sayısını döner (0 ya da 1)")
    @GetMapping(GET_CAREER_EDUCATION_COUNT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getCareerEducationCount(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(careerEducationService.getCareerEducationCount(studentId));
    }

    @Operation(summary = "Kariyer eğitimi kaydetme işlemi", description = "İstihdam menüsündeki Kariyer Eğitimi " +
            "sayfasında yapılan kariyer eğitimi kaydetme işlemidir.")
    @PostMapping(SAVE_CAREER_EDUCATION)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveCareerEducation(@Valid @RequestBody SaveCareerEducationRequestDto dto) {
        return ResponseEntity.ok(careerEducationService.saveCareerEducation(dto));
    }

    @Operation(summary = "Kariyer eğitimi sayfasında kariyer eğitimini günceller", description = "İstihdam menüsünde " +
            "Kariyer Eğitimi sayfasında öğrencinin daha önce girilmiş kariyer eğitimi varsa üstüne kaydeder")
    @PutMapping(UPDATE_CAREER_EDUCATION)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateCareerEducation(@Valid @RequestBody UpdateCareerEducationRequestDto dto) {
        return ResponseEntity.ok(careerEducationService.updateCareerEducation(dto));
    }

    @Operation(summary = "Kariyer eğitimi sayfasında mevcut puanları getirir", description = "İstihdam menüsünde " +
            "Kariyer Eğitimi başlığında öğrencinin önceden kaydedilmiş kariyer eğitimi puan değerleri varsa getirir")
    @GetMapping(GET_CAREER_EDUCATION + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetCareerEducationResponseDto> getCareerEducation(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(careerEducationService.getCareerEducation(studentId));
    }

    @Operation(summary = "Kariyer eğitimi sayfasında ortalama puanı getirir", description = "İstihdam menüsünde " +
            "Kariyer Eğitimi sayfasında öğrencinin ortalama kariyer eğitimi puan bilgisini döner")
    @GetMapping(GET_CAREER_EDUCATION_AVERAGE_POINT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getCareerEducationAveragePoint(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(careerEducationService.getCareerEducationAveragePoint(studentId));
    }

}
