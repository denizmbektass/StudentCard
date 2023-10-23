package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGraduationProjectRequestDto {
    private int meetingAttendance;
    private int teamworkCompatibility;
    private int numberOfCompletedTasks;
    private int interestLevel;
    private int presentation;
    private int retroScore;
    private int meetingAttendancePercentage ;
    private int teamworkCompatibilityPercentage ;
    private int numberOfCompletedTasksPercentage ;
    private int interestLevelPercentage ;
    private int presentationPercentage ;
    private int retroScorePercentage ;
    @NotNull
    private String studentToken;
}
