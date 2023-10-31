package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerAssessmentForTranscriptResponseDto {
    private String assessmentName;
    private double totalTrainerAssessmentScore;
    private double score;
    private String description;
}
