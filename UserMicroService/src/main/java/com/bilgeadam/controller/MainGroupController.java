package com.bilgeadam.controller;

import com.bilgeadam.repository.entity.MainGroup;
import com.bilgeadam.repository.view.VwGroupName;
import com.bilgeadam.service.MainGroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/main-group")
public class MainGroupController {
    private final MainGroupService mainGroupService;


    @Operation(summary = "Ana grup kaydetme işlemi",
            description = "Belirtilen ana grup adını kullanarak bir ana grup kaydeder.")
    @CrossOrigin("*")
    @PostMapping("/save/{mainGroupName}")
    public ResponseEntity<Boolean> save(@PathVariable String mainGroupName){
        return ResponseEntity.ok(mainGroupService.saveGroup(mainGroupName));
    }


    @Operation(summary = "Tüm grup adlarını alma işlemi",
            description = "Sisteme kayıtlı tüm grup adlarını getirir.")
    @CrossOrigin("*")
    @GetMapping("/get-all-group-names")
    public ResponseEntity<List<VwGroupName>> getAllGroupNames(){
        return ResponseEntity.ok(mainGroupService.getAllGroupNames());
    }


    @Operation(summary = "Ana grup ID'sine göre alt grup adlarını alma işlemi",
            description = "Belirtilen ana grup ID'sine göre alt grup adlarını getirir.")
    @CrossOrigin("*")
    @GetMapping("/get-all-group-names-with-group-id/{mainGroupId}")
    public ResponseEntity<Set<String>> getSubGroupNamesByMainGroupId(@PathVariable String mainGroupId) {
         return ResponseEntity.ok(mainGroupService.getSubGroupNamesByMainGroupId(mainGroupId));
    }






}
