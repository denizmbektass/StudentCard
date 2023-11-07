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
public class UpdateTeamLeadAssessmentRequestDto {
    @NotEmpty
    private String studentToken;
    @Min(0)
    @Max(100)
    @NotNull
    private Double score;
    private String assessment;
    private Double successScore;
}
