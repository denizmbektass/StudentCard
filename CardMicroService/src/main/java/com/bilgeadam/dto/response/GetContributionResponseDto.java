package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetContributionResponseDto {

    private Double incorrectCodeOrDisplayMessageNote;
    private Double documentationForBacklogNote;
    private Double researchNote;
    private Double intraTeamTrainingNote;
    private Double totalScoreContribution;
}
