package com.bilgeadam.controller;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import com.bilgeadam.service.InternshipSuccessRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class InternshipSuccessRateController {
    private final InternshipSuccessRateService internshipSuccessRateService;

    public ResponseEntity<InternshipSuccessRate> addScoreAndCommentForStudent(InternshipSuccessRateRequestDto dto) {
        return ResponseEntity.ok(internshipSuccessRateService.addScoreAndCommentForStudent(dto));
    }
}
