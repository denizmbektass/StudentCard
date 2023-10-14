package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.dto.response.ShowUserAbsenceInformationResponseDto;
import com.bilgeadam.service.AbsenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ABSENCE)
public class AbsenceController {
    private final AbsenceService absenceService;
    //@PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Kayıt işlemi",
            description = "Belirtilen DTO ile bir kaydı kaydetmek için kullanılır.")
    @CrossOrigin("*")
    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody AddAbsenceRequestDto dto){
        return ResponseEntity.ok(absenceService.save(dto));
    }


    @Operation(summary = "Kullanıcının izin bilgilerini görüntüleme işlemi",
            description = "Belirtilen token kullanılarak kullanıcının izin bilgilerini görüntüler.")
    @CrossOrigin("*")
    @GetMapping("/show-user-absence-information/{token}")
    public ResponseEntity<ShowUserAbsenceInformationResponseDto> showUserAbsenceInformation(@PathVariable String token){
        return ResponseEntity.ok(absenceService.showUserAbsenceInformation(token));
    }





}
