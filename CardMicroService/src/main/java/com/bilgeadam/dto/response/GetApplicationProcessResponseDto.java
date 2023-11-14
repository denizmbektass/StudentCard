package com.bilgeadam.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetApplicationProcessResponseDto {
    private int jobApplicationScore;
    private int informationSharingScore;
    private int referralParticipationScore;
    private int companyFitScore;
    private int careerTeamAssessment;
    private double totalScore;
}
