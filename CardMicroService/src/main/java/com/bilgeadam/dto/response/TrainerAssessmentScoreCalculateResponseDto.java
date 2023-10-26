package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerAssessmentScoreCalculateResponseDto {

    private String assessmentName;
    private double totalTrainerAssessmentScore;
    private String description;
}
