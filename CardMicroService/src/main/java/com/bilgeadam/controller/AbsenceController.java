package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.service.AbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ABSENCE)
public class AbsenceController {
    private final AbsenceService absenceService;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody AddAbsenceRequestDto dto){
        return ResponseEntity.ok(absenceService.save(dto));
    }

    @CrossOrigin("*")
    @GetMapping("/show-user-absence-information/{userId}")
    public ResponseEntity<Double> showUserAbsenceInformation(@PathVariable String userId){
        return ResponseEntity.ok(absenceService.showUserAbsenceInformation(userId));
    }





}
