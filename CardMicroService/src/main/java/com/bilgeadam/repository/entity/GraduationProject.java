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
    private Double meetingAttendance;
    private Double teamworkCompatibility;
    private Double numberOfCompletedTasks;
    private Double interestLevel;
    private Double presentation;
    private Double retroScore;
    private Double averageScore;
    private String studentId;
    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
    private Double meetingAttendancePercentage ;
    private Double teamworkCompatibilityPercentage ;
    private Double numberOfCompletedTasksPercentage ;
    private Double interestLevelPercentage ;
    private Double presentationPercentage ;
    private Double retroScorePercentage ;
}
