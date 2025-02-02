package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.dto.response.ShowStudentAbsenceInformationResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
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
    @Operation(summary = "Kayıt işlemi",
            description = "Belirtilen DTO ile bir kaydı kaydetmek için kullanılır.")
    @CrossOrigin("*")
    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('master_trainer:write','assistant_trainer:write')")
    public ResponseEntity<Boolean> save(@RequestBody AddAbsenceRequestDto dto){
        return ResponseEntity.ok(absenceService.save(dto));
    }


    @Operation(summary = "Kullanıcının izin bilgilerini görüntüleme işlemi",
            description = "Belirtilen token kullanılarak kullanıcının izin bilgilerini görüntüler.")
    @CrossOrigin("*")
    @GetMapping("/show-user-absence-information/{token}")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseEntity<ShowStudentAbsenceInformationResponseDto> showUserAbsenceInformation(@PathVariable String token){
        return ResponseEntity.ok(absenceService.showStudentAbsenceInformation(token));
    }

    @Operation(summary = "BaseApi tarafından yoklama verilerinin çekilmesi işlemi",
            description = "BaseApi tarafından fake yoklama bilgisi verilerini çekip veritabanına kaydeder.")
    @GetMapping("/get-all-base-absences")
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseEntity<String> getAllBaseAbsences(){
        try {
            absenceService.getAllBaseAbsences();
            return ResponseEntity.ok("Yoklama bilgileri veritabanına eklendi.");
        } catch (Exception e){
            throw new CardServiceException(ErrorType.INTERNAL_ERROR);
        }
    }



}
