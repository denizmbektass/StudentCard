package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatProjectBehaviorScoreRequestDto {

    private String token;
    private String studentId;
    private Long rapportScore;
    private Long interestScore;
    private Long presentationScore;
    private Long retroScore;
    private Long rapportScorePercentage ;
    private Long interestScorePercentage ;
    private Long presentationScorePercentage ;
    private Long retroScorePercentage ;
}
