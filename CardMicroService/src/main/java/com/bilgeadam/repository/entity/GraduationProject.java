package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class GraduationProject extends BaseEntity{
    @Id
    private String graduationId;
    private int meetingAttendance;
    private int teamworkCompatibility;
    private int numberOfCompletedTasks;
    private int interestLevel;
    private int presentation;
    private int retroScore;
    private double averageScore;
    private String studentId;
    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
    private int meetingAttendancePercentage ;
    private int teamworkCompatibilityPercentage ;
    private int numberOfCompletedTasksPercentage ;
    private int interestLevelPercentage ;
    private int presentationPercentage ;
    private int retroScorePercentage ;
}
