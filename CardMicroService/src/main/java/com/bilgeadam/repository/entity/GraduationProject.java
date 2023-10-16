package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
