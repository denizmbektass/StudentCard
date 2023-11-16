package com.bilgeadam.controller;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.dto.response.AttendanceSearchResponseDto;
import com.bilgeadam.dto.response.FindAllUnRegisteredGroupListResponseDto;
import com.bilgeadam.dto.response.FindByMainGroupIdResponseDto;
import com.bilgeadam.repository.entity.InternshipGroup;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.service.InternshipGroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card/internship-group")
public class InternshipGroupController {
    private final InternshipGroupService internshipGroupService;

    @Operation(summary = "Grup kaydetme işlemi",
            description = "Belirtilen DTO kullanılarak bir grup kaydeder.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PostMapping("/save")
    public ResponseEntity<Boolean> saveGroup(@RequestBody GroupSaveRequestDto dto){
        return ResponseEntity.ok(internshipGroupService.saveGroup(dto));
    }


    @Operation(summary = "Tüm staj gruplarını alma işlemi",
            description = "Tüm staj gruplarını alır.")
    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<List<InternshipGroup>> findAll(){
        return ResponseEntity.ok(internshipGroupService.findAll());
    }

    @Operation(summary = "Ana grup ID'sine göre staj gruplarını bulma işlemi",
            description = "Belirtilen ana grup ID'sine sahip staj gruplarını bulur.")
    @CrossOrigin
    @GetMapping("/find-by-main-group-id/{mainGroupId}")
    public ResponseEntity<List<FindByMainGroupIdResponseDto>> findByMainGroupId(@PathVariable String mainGroupId){
        return ResponseEntity.ok(internshipGroupService.findByMainGroupId(mainGroupId));
    }

    @Operation(summary = "Katılım arama listesini gösterme işlemi",
            description = "Belirtilen grup ID'sine sahip katılım arama listesini gösterir.")
    @CrossOrigin
    @GetMapping("/show-attendance-search-list/{groupId}")
    public ResponseEntity<List<AttendanceSearchResponseDto>> showAttendanceSearchList(@PathVariable String groupId){
        return ResponseEntity.ok(internshipGroupService.showAttendanceSearchList(groupId));
    }


    @Operation(summary = "Kaydedilmemiş grup listesini alma işlemi",
            description = "Belirtilen ana grup ID'sine sahip kaydedilmemiş grup listesini alır.")
    @CrossOrigin
    @GetMapping("/find-all-unregistered-group-list/{mainGroupId}")
    public ResponseEntity<List<FindAllUnRegisteredGroupListResponseDto>> findAllUnRegisteredGroupList(@PathVariable String mainGroupId){
        return ResponseEntity.ok(internshipGroupService.findAllUnRegisteredGroupList(mainGroupId));
    }

    @Operation(summary = "Kaydedilmiş grup listesini alma işlemi",
            description = "Belirtilen ana grup ID'sine sahip kaydedilmiş grup listesini alır.")
    @CrossOrigin
    @GetMapping("/find-all-registered-group-list/{mainGroupId}")
    public ResponseEntity<List<InternshipGroup>> findAllRegisteredGroupList(@PathVariable String mainGroupId) {
        return ResponseEntity.ok(internshipGroupService.findAllRegisteredGroupList(mainGroupId));
    }





}
