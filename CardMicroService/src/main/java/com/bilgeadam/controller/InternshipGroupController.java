package com.bilgeadam.controller;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.dto.response.AttendanceSearchResponseDto;
import com.bilgeadam.dto.response.FindAllUnRegisteredGroupListResponseDto;
import com.bilgeadam.dto.response.FindByMainGroupIdResponseDto;
import com.bilgeadam.repository.entity.InternshipGroup;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.service.InternshipGroupService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internship-group")
public class InternshipGroupController {
    private final InternshipGroupService internshipGroupService;
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    @PostMapping("/save")
    public ResponseEntity<Boolean> saveGroup(@RequestBody GroupSaveRequestDto dto){
        return ResponseEntity.ok(internshipGroupService.saveGroup(dto));
    }

    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<List<InternshipGroup>> findAll(){
        return ResponseEntity.ok(internshipGroupService.findAll());
    }

    @CrossOrigin
    @GetMapping("/find-by-main-group-id/{mainGroupId}")
    public ResponseEntity<List<FindByMainGroupIdResponseDto>> findByMainGroupId(@PathVariable String mainGroupId){
        return ResponseEntity.ok(internshipGroupService.findByMainGroupId(mainGroupId));
    }

    @CrossOrigin
    @GetMapping("/show-attendance-search-list/{groupId}")
    public ResponseEntity<List<AttendanceSearchResponseDto>> showAttendanceSearchList(@PathVariable String groupId){
        return ResponseEntity.ok(internshipGroupService.showAttendanceSearchList(groupId));
    }

    @CrossOrigin
    @GetMapping("/find-all-unregistered-group-list/{mainGroupId}")
    public ResponseEntity<List<FindAllUnRegisteredGroupListResponseDto>> findAllUnRegisteredGroupList(@PathVariable String mainGroupId){
        return ResponseEntity.ok(internshipGroupService.findAllUnRegisteredGroupList(mainGroupId));
    }

    @CrossOrigin
    @GetMapping("/find-all-registered-group-list/{mainGroupId}")
    public ResponseEntity<List<InternshipGroup>> findAllRegisteredGroupList(@PathVariable String mainGroupId) {
        return ResponseEntity.ok(internshipGroupService.findAllRegisteredGroupList(mainGroupId));
    }





}
