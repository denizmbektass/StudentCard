package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProjectBehaviorResponseDto {

    private Long rapportScore;
    private Long interestScore;
    private Long presentationScore;
    private Long retroScore;
    private Double averageScore;
    private Long rapportScorePercentage ;
    private Long interestScorePercentage ;
    private Long presentationScorePercentage ;
    private Long retroScorePercentage;
}
