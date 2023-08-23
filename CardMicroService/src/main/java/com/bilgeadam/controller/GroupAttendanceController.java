package com.bilgeadam.controller;

import com.bilgeadam.dto.request.GroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.response.GroupStudentAttendanceResponseDto;
import com.bilgeadam.service.GroupAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group-attendance")
public class GroupAttendanceController {
    private final GroupAttendanceService groupAttendanceService;

    @CrossOrigin
    @PostMapping("/show-group-student-attendance")
    public ResponseEntity<GroupStudentAttendanceResponseDto> showGroupStudentAttendance(@RequestBody @Valid GroupStudentAttendanceRequestDto dto){
        return ResponseEntity.ok(groupAttendanceService.showGroupStudentAttendance(dto));
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PutMapping("/update-group-student-attendance")
    public ResponseEntity<Boolean> updateGroupAttendance(@RequestBody @Valid UpdateGroupStudentAttendanceRequestDto dto){
        return ResponseEntity.ok(groupAttendanceService.updateGroupAttendance(dto));
    }
}
