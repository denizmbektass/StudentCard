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
public class UpdateEducationWeightsRequestDto {
    @NotNull
    public String educationWeightsId;
    @NotNull
    private String groupName;
    @Min(0)
    @Max(100)
    @NotNull
    public Double examWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double projectBehaviorWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double assessmentWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double assignmentWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double oralExamWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double obligationWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double graduationProjectWeight;
}
