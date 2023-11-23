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
    private double incorrectCodeOrDisplayMessageNote;
    private double documentationForBacklogNote;
    private double researchNote;
    private double intraTeamTrainingNote;
}
