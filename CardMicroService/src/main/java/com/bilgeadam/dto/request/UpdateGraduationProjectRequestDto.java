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
public class UpdateGraduationProjectRequestDto {
    @NotNull
    private String studentToken;
    @Min(0)
    @Max(100)
    @NotNull
    private Double meetingAttendance;
    @Min(0)
    @Max(100)
    @NotNull
    private Double teamworkCompatibility;
    @Min(0)
    @Max(100)
    @NotNull
    private Double numberOfCompletedTasks;
    @Min(0)
    @Max(100)
    @NotNull
    private Double interestLevel;
    @Min(0)
    @Max(100)
    @NotNull
    private Double presentation;
    @Min(0)
    @Max(100)
    @NotNull
    private Double retroScore;
    @Min(0)
    @Max(100)
    @NotNull
    private Double meetingAttendancePercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double teamworkCompatibilityPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double numberOfCompletedTasksPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double interestLevelPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double presentationPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double retroScorePercentage ;
}
