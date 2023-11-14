package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationProcessRequestDto {
    @NotEmpty
    private String studentToken;
    private int jobApplicationScore;
    private int informationSharingScore;
    private int referralParticipationScore;
    private int companyFitScore;
    private int careerTeamAssessment;
}
