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
public class TrainerAssessmentCoefficients extends BaseEntity {

    @Id
    private String trainerAssessmentCoefficientsId;
    private double behaviorInClassCoefficient;
    private double courseInterestLevelCoefficient;
    private double cameraOpeningGradeCoefficient;
    private double instructorGradeCoefficient;
    private double dailyHomeworkGradeCoefficient;
    private double averageScore;
    private String studentId;
    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
    private double behaviorInClassCoefficientPercentage ;
    private double courseInterestLevelCoefficientPercentage ;
    private double cameraOpeningGradeCoefficientPercentage ;
    private double instructorGradeCoefficientPercentage ;
    private double dailyHomeworkGradeCoefficientPercentage ;
}