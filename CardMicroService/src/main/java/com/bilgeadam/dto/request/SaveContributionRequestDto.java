package com.bilgeadam.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveContributionRequestDto {

    @NotEmpty
    private String studentToken;

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
}
