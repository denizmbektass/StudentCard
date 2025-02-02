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
    public Double tasksWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double teamworkWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double personalMotivationWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double contributionWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double attendanceWeight;
    @Min(0)
    @Max(100)
    @NotNull
    public Double teamLeaderWeight;
}
