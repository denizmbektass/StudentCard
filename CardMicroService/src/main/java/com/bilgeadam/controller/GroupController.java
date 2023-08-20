package com.bilgeadam.controller;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.dto.response.AttendanceSearchResponseDto;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.service.GroupService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    @CrossOrigin
    @PostMapping("/save")
    public ResponseEntity<Boolean> saveGroup(@RequestBody GroupSaveRequestDto dto){
        return ResponseEntity.ok(groupService.saveGroup(dto));
    }

    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<List<VwGroupResponseDto>> showGroupList(){
        return ResponseEntity.ok(groupService.showGroupList());
    }

    @CrossOrigin
    @GetMapping("/show-attendance-search-list/{groupId}")
    public ResponseEntity<List<AttendanceSearchResponseDto>> showAttendanceSearchList(@PathVariable String groupId){
        return ResponseEntity.ok(groupService.showAttendanceSearchList(groupId));
    }

}
