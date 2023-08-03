package com.bilgeadam.controller;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.dto.response.InternshipResponseDto;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import com.bilgeadam.service.InternshipSuccessRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERNSHIP)
@RequiredArgsConstructor
public class InternshipSuccessRateController {
    private final InternshipSuccessRateService internshipSuccessRateService;

    @PostMapping(ADD_SCORE_AND_COMMENT)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> addScoreAndCommentForStudent(@RequestBody InternshipSuccessRateRequestDto dto) {
        return ResponseEntity.ok(internshipSuccessRateService.addScoreAndCommentForStudent(dto));
    }

    @GetMapping("/find-all-internship-with-user/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<InternshipResponseDto>> findAllInternshipWithUser(@PathVariable String token) {
        return ResponseEntity.ok(internshipSuccessRateService.findAllInternshipWithUser(token));
    }

    @DeleteMapping("/delete-selected-internship/{internshipId}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> deleteSelectedInternship(@PathVariable String internshipId) {
        return ResponseEntity.ok(internshipSuccessRateService.deleteSelectedInternship(internshipId));
    }

}
