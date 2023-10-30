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
public class TrainerAssessment extends BaseEntity{

    @Id
    private String trainerAssessmentId;

    private String trainerAssessmentIdCoefficientsId;
    private String studentId;
    private String assessmentName;
    private String description;
    private double behaviorInClass;
    private double courseInterestLevel;
    private double cameraOpeningGrade;
    private double instructorGrade;
    private double dailyHomeworkGrade;
    private double totalTrainerAssessmentScore;

    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
}
