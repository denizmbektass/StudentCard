package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerAssessmentSaveResponseDto {

    private String assessmentName;
    private String studentToken;
    private String studentId;
    private String description;
    private double totalTrainerAssessmentScore;
}
