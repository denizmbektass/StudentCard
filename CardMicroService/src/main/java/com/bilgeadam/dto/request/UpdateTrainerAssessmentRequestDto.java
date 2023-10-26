package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerAssessmentRequestDto {

    private String assessmentId;
    private double totalTrainerAssessmentScore;
    private String description;
}
