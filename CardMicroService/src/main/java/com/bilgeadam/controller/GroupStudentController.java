package com.bilgeadam.controller;

import com.bilgeadam.dto.request.GroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentRequestDto;
import com.bilgeadam.dto.response.GroupStudentAttendanceResponseDto;
import com.bilgeadam.dto.response.GroupStudentsResponseDto;
import com.bilgeadam.dto.response.ShowGroupInformationListResponseDto;
import com.bilgeadam.repository.entity.InternshipGroup;
import com.bilgeadam.service.GroupStudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card/group-student")
public class GroupStudentController {
    private final GroupStudentService groupStudentService;


    @Operation(summary = "Gruba öğrenci ekleme işlemi",
            description = "Belirtilen DTO kullanılarak bir gruba öğrenci ekler.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PostMapping("/save-group-student")
    public ResponseEntity<Boolean> saveGroupStudent(@RequestBody SaveGroupStudentRequestDto dto){
        return ResponseEntity.ok(groupStudentService.saveAddGroupStudent(dto));
    }

    @Operation(summary = "Tüm grup öğrencilerini alma işlemi",
            description = "Tüm grup öğrencilerini alır.")
    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<List<GroupStudentsResponseDto>> findAll(){
        return ResponseEntity.ok(groupStudentService.findAllGroupStudentList());
    }


    @Operation(summary = "Gruptaki öğrenciyi ID'ye göre silme işlemi",
            description = "Belirtilen gruptaki öğrencinin ID'sini kullanarak o gruptaki öğrencisiyi siler.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @DeleteMapping("/delete-by-id/{groupStudentId}")
    public ResponseEntity<Boolean> deleteGroupStudentById(@PathVariable String groupStudentId){
        return ResponseEntity.ok(groupStudentService.deleteGroupStudentById(groupStudentId));
    }

    @Operation(summary = "Gruptaki öğrenciyi güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak bir gruptaki öğrenciyi günceller.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PutMapping("/update-group-student")
    public ResponseEntity<Boolean> updateGroupStudent(@RequestBody UpdateGroupStudentRequestDto dto){
        return ResponseEntity.ok(groupStudentService.updateGroupStudent(dto));
    }


    @Operation(summary = "Grup bilgilerini listeleme işlemi",
            description = "Tüm grup bilgilerini bir liste halinde döner.")
    @CrossOrigin
    @GetMapping("/show-group-information-list")
    public ResponseEntity<List<ShowGroupInformationListResponseDto>> showGroupInformationList(){
        return ResponseEntity.ok(groupStudentService.showGroupInformationList());
    }


    @Operation(summary = "Gruptaki öğrencilerin katılım durumunu gösterme işlemi",
            description = "Belirtilen DTO kullanılarak gruptaki öğrencilerin katılım durumunu gösterir.")
    @CrossOrigin
    @PostMapping("/show-group-student-attendance")
    public ResponseEntity<GroupStudentAttendanceResponseDto> showGroupStudentAttendance(@RequestBody @Valid GroupStudentAttendanceRequestDto dto){
        return ResponseEntity.ok(groupStudentService.showGroupStudentAttendance(dto));
    }


    @Operation(summary = "Gruptaki öğrencilerin katılım durumunu güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak gruptaki öğrencilerin katılım durumunu günceller.")
    @CrossOrigin
    @PutMapping("/update-group-student-attendance")
    public ResponseEntity<Boolean> updateGroupAttendance(@RequestBody @Valid UpdateGroupStudentAttendanceRequestDto dto){
        return ResponseEntity.ok(groupStudentService.updateGroupAttendance(dto));
    }


    @Operation(summary = "Kayıtlı grup listesini silme işlemi",
            description = "Belirtilen staj grubunun kayıtlı grup listesini siler.")
    @CrossOrigin
    @DeleteMapping("/delete-registered-group-list/{internshipGroupId}")
    public ResponseEntity<Boolean> deleteRegisteredGroupList(@PathVariable String internshipGroupId) {
        return ResponseEntity.ok(groupStudentService.deleteRegisteredGroupList(internshipGroupId));
    }





}
