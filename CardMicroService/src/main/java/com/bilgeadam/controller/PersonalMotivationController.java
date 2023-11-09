package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreatePersonalMotivationRequestDto;
import com.bilgeadam.dto.request.UpdatePersonalMotivationRequestDto;
import com.bilgeadam.dto.response.GetPersonalMotivationResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.PersonalMotivationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(PERSONAL_MOTIVATION)
public class PersonalMotivationController {

    private final PersonalMotivationService personalMotivationService;

    @Operation(summary = "Davranış puanlama işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> createPersonalMotivation(@RequestBody @Valid CreatePersonalMotivationRequestDto dto){
        return ResponseEntity.ok(personalMotivationService.createPersonalMotivation(dto));
    }

    @Operation(summary = "Davranış puanlarını güncelleme işlemi",
            description = "Belirtilen öğrenci kimliği kullanılarak sınavlar için ortalama hesaplar.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updatePersonalMotivation(@RequestBody @Valid UpdatePersonalMotivationRequestDto dto){
        return  ResponseEntity.ok(personalMotivationService.updatePersonalMotivation(dto));
    }
    @Operation(summary = "Davranış puanı silme işlemi",
            description = "Belirtilen davranış puanlama kimliği kullanılarak bir davranış puanlarını siler.")
    @DeleteMapping(DELETE+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> deletePersonalMotivation(@PathVariable String token){
        return ResponseEntity.ok(personalMotivationService.deletePersonalMotivation(token));
    }

    @Operation(summary = "Davranış puanlarını  alma işlemi",
            description = "Belirtilen token kullanılarak proje davranış puanlarını getirir.")
    @GetMapping(FIND_PERSONAL_MOTIVATION+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<GetPersonalMotivationResponseDto> findPersonalMotivation(@PathVariable String token){
        return  ResponseEntity.ok(personalMotivationService.findPersonalMotivation(token));
    }
}
