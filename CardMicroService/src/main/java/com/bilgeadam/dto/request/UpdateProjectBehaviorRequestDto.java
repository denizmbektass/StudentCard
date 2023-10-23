package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class UpdateProjectBehaviorRequestDto {


    private String studentToken;
    private String projectBehaviorId;
    private Long rapportScore;
    private Long interestScore;
    private Long presentationScore;
    private Long retroScore;
    private Long rapportScorePercentage ;
    private Long interestScorePercentage ;
    private Long presentationScorePercentage ;
    private Long retroScorePercentage ;


}
