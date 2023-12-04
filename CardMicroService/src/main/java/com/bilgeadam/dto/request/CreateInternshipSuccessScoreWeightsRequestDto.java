package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInternshipSuccessScoreWeightsRequestDto {
    @NotNull
    private String groupName;
    @Min(0)
    @Max(100)
    @NotNull
    public Double writtenExamWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double algorithmWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double candidateInterviewWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double technicalInterviewWeight;
}
