package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetGraduationProjectResponseDto {
    private int meetingAttendance;
    private int teamworkCompatibility;
    private int numberOfCompletedTasks;
    private int interestLevel;
    private int presentation;
    private int retroScore;
    private double averageScore;
    private int meetingAttendancePercentage ;
    private int teamworkCompatibilityPercentage ;
    private int numberOfCompletedTasksPercentage ;
    private int interestLevelPercentage ;
    private int presentationPercentage ;
    private int retroScorePercentage ;
}
