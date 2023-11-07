package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveTeamworkRequestDto;
import com.bilgeadam.dto.request.UpdateTeamworkRequestDto;
import com.bilgeadam.dto.response.GetTeamworkResponseDto;
import com.bilgeadam.service.TeamworkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TEAMWORK)
public class TeamworkController {
    private final TeamworkService teamworkService;

    @Operation(summary = "Ekip Çalışması sayfasında db'deki kariyer eğitimi kaydı sayısını döner",
            description = "Staj menüsünde Ekip Çalışması sayfasında öğrencinin DB'de kayıtlı ekip çalışması" +
                    "sayısını döner (0 ya da 1)")
    @GetMapping(GET_TEAMWORK_COUNT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getTeamworkCount(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(teamworkService.getTeamworkCount(studentId));
    }

    @Operation(summary = "Ekip çalışması kaydetme işlemi", description = "Staj menüsündeki Ekip Çalışması " +
            "sayfasında yapılan ekip çalışması kaydetme işlemidir.")
    @PostMapping(SAVE_TEAMWORK)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveTeamwork(@Valid @RequestBody SaveTeamworkRequestDto dto) {
        return ResponseEntity.ok(teamworkService.saveTeamwork(dto));
    }

    @Operation(summary = "Ekip Çalışması sayfasında ekip çalışmasını günceller", description = "Staj menüsünde " +
            "Ekip Çalışması sayfasında öğrencinin daha önce girilmiş ekip çalışması varsa üstüne kaydeder")
    @PutMapping(UPDATE_TEAMWORK)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateTeamwork(@Valid @RequestBody UpdateTeamworkRequestDto dto) {
        return ResponseEntity.ok(teamworkService.updateTeamwork(dto));
    }

    @Operation(summary = "Ekip Çalışması sayfasında mevcut puanları getirir", description = "Staj menüsünde " +
            "Ekip Çalışması başlığında öğrencinin önceden kaydedilmiş ekip çalışması puan değerleri varsa getirir")
    @GetMapping(GET_TEAMWORK + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetTeamworkResponseDto> getTeamwork(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(teamworkService.getTeamwork(studentId));
    }

    @Operation(summary = "Ekip Çalışması sayfasında ortalama puanı getirir", description = "Staj menüsünde " +
            "Ekip Çalışması sayfasında öğrencinin ekip çalışması başarı puan bilgisini döner")
    @GetMapping(GET_TEAMWORK_SUCCESS_POINT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getTeamworkSuccessPoint(@PathVariable @NotEmpty String studentId) {
        return ResponseEntity.ok(teamworkService.getTeamworkSuccessPoint(studentId));
    }
}
