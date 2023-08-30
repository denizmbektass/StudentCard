package com.bilgeadam.controller;

import com.bilgeadam.dto.request.GroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentRequestDto;
import com.bilgeadam.dto.response.GroupStudentAttendanceResponseDto;
import com.bilgeadam.dto.response.GroupStudentsResponseDto;
import com.bilgeadam.dto.response.ShowGroupInformationListResponseDto;
import com.bilgeadam.service.GroupStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group-student")
public class GroupStudentController {
    private final GroupStudentService groupStudentService;
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PostMapping("/save-group-student")
    public ResponseEntity<Boolean> saveGroupStudent(@RequestBody SaveGroupStudentRequestDto dto){
        return ResponseEntity.ok(groupStudentService.saveAddGroupStudent(dto));
    }

    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<List<GroupStudentsResponseDto>> findAll(){
        return ResponseEntity.ok(groupStudentService.findAllGroupStudentList());
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @DeleteMapping("/delete-by-id/{groupStudentId}")
    public ResponseEntity<Boolean> deleteGroupStudentById(@PathVariable String groupStudentId){
        return ResponseEntity.ok(groupStudentService.deleteGroupStudentById(groupStudentId));
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PutMapping("/update-group-student")
    public ResponseEntity<Boolean> updateGroupStudent(@RequestBody UpdateGroupStudentRequestDto dto){
        return ResponseEntity.ok(groupStudentService.updateGroupStudent(dto));
    }

    @CrossOrigin
    @GetMapping("/show-group-information-list")
    public ResponseEntity<List<ShowGroupInformationListResponseDto>> showGroupInformationList(){
        return ResponseEntity.ok(groupStudentService.showGroupInformationList());
    }

    @CrossOrigin
    @PostMapping("/show-group-student-attendance")
    public ResponseEntity<GroupStudentAttendanceResponseDto> showGroupStudentAttendance(@RequestBody @Valid GroupStudentAttendanceRequestDto dto){
        return ResponseEntity.ok(groupStudentService.showGroupStudentAttendance(dto));
    }
    /*
    @CrossOrigin
    @PutMapping("/update-group-student-attendance")
    public ResponseEntity<Boolean> updateGroupAttendance(@RequestBody @Valid UpdateGroupStudentAttendanceRequestDto dto){
        return ResponseEntity.ok(groupStudentService.updateGroupAttendance(dto));
    }
*/




}
