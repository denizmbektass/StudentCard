package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGraduationProjectRequestDto {
    private int meetingAttendance;
    private int teamworkCompatibility;
    private int numberOfCompletedTasks;
    private int interestLevel;
    private int presentation;
    private int retroScore;
    private double averageScore;
    @NotNull
    private String studentToken;
}
