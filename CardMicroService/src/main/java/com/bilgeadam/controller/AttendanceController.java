package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateAttendanceRequestDto;
import com.bilgeadam.dto.response.GetAttendanceResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(ATTENDANCE)
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;


    @Operation(summary = "Staj Katılım puanları oluşturma işlemi",
                description = "Belirtilen dto kullanılarak staj katılım oranı puanlandırılır.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('internship_team:write')")
    public ResponseEntity<MessageResponse> createAttendanceScore(@RequestBody @Valid CreateAttendanceRequestDto dto){
        return ResponseEntity.ok(attendanceService.createAttendanceScore(dto));
    }


    @Operation(summary = "Staj Katılım puanları güncelleme işlemi",
                description = "Belirtilen dto ve studentId kullanılarak staj katılım puanları güncelleniyor.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('internship_team:write')")
    public ResponseEntity<MessageResponse> updateAttendanceScore(@RequestBody @Valid CreateAttendanceRequestDto dto){
        return ResponseEntity.ok(attendanceService.updateAttendanceScore(dto));
    }


    @Operation(summary = "Staj Katılım puan bilgisi getirme işlemi",
                description = "Belirtilen token kullanılarak öğrenciye ait staj katılım bilgisi getiriliyor.")
    @GetMapping(GET_ATTENDANCE_SCORE)
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<GetAttendanceResponseDto> getAttendanceInfo(@PathVariable String token){
        return ResponseEntity.ok(attendanceService.getAttendanceInfo(token));
    }


    @Operation(summary = "Staj Katılım puan bilgisi silme işlemi",
                description = "Belirtilen token kullanılarak öğrenciye ait staj katılım bilgisi siliniyor.")
    @DeleteMapping(DELETE + "/{token}")
    @CrossOrigin("*")
    @PreAuthorize("hasAuthority('internship_team:write')")
    public ResponseEntity<MessageResponse> deleteAttendanceScore(@PathVariable String token){
        return ResponseEntity.ok(attendanceService.deleteAttendanceScore(token));
    }

}
