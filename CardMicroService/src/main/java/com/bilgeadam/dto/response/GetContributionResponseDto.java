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

    @Min(0)
    @Max(100)
    @NotNull
    private double incorrectCodeOrDisplayMessageNote;

    @Min(0)
    @Max(100)
    @NotNull
    private double documentationForBacklogNote;

    @Min(0)
    @Max(100)
    @NotNull
    private double researchNote;

    @Min(0)
    @Max(100)
    @NotNull
    private double intraTeamTrainingNote;

    @Min(0)
    @Max(100)
    @NotNull
    private double totalScoreContribution;
}
