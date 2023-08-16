package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.repository.view.VwGroupStudentResponseDto;
import com.bilgeadam.service.GroupStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group-student")
public class GroupStudentController {
    private final GroupStudentService groupStudentService;

    @CrossOrigin
    @PostMapping("/save-group-student")
    public ResponseEntity<Boolean> saveGroupStudent(@RequestBody SaveGroupStudentRequestDto dto){
        return ResponseEntity.ok(groupStudentService.saveAddGroupStudent(dto));
    }

    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<List<VwGroupStudentResponseDto>> findAll(){
        return ResponseEntity.ok(groupStudentService.findAllGroupStudentList());
    }

    @CrossOrigin
    @DeleteMapping("/delete-by-id/{groupStudentId}")
    public ResponseEntity<Boolean> deleteGroupStudentById(@PathVariable String groupStudentId){
        return ResponseEntity.ok(groupStudentService.deleteGroupStudentById(groupStudentId));
    }





}
