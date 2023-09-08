package com.bilgeadam.controller;

import com.bilgeadam.repository.entity.MainGroup;
import com.bilgeadam.repository.view.VwGroupName;
import com.bilgeadam.service.MainGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main-group")
public class MainGroupController {
    private final MainGroupService mainGroupService;

    @CrossOrigin("*")
    @PostMapping("/save/{mainGroupName}")
    public ResponseEntity<Boolean> save(@PathVariable String mainGroupName){
        return ResponseEntity.ok(mainGroupService.saveGroup(mainGroupName));
    }

    @CrossOrigin("*")
    @GetMapping("/get-all-group-names")
    public ResponseEntity<List<VwGroupName>> getAllGroupNames(){
        return ResponseEntity.ok(mainGroupService.getAllGroupNames());
    }

    @CrossOrigin("*")
    @GetMapping("/get-all-group-names-with-group-id/{mainGroupId}")
    public ResponseEntity<Set<String>> getSubGroupNamesByMainGroupId(@PathVariable String mainGroupId) {
         return ResponseEntity.ok(mainGroupService.getSubGroupNamesByMainGroupId(mainGroupId));
    }






}
