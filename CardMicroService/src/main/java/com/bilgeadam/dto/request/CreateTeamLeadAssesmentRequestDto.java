package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTeamLeadAssesmentRequestDto {
    @NotEmpty
    private String studentToken;
    @Min(0)
    @Max(100)
    private Double score;
    private String assessment;
}
