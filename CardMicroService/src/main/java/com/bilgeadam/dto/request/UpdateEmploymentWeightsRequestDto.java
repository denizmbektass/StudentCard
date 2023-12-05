package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmploymentWeightsRequestDto {
    @NotNull
    public String employmentWeightsId;
    @NotNull
    private String groupName;
    @Min(0)
    @Max(100)
    @NotNull
    private Double documentSubmitWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double careerEducationWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double employmentInterviewWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double applicationProcessWeight;
}
