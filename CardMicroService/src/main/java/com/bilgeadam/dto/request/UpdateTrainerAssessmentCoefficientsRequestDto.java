package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerAssessmentCoefficientsRequestDto {

    @NotNull
    private String studentToken;
    private double behaviorInClassCoefficient;
    private double courseInterestLevelCoefficient;
    private double cameraOpeningGradeCoefficient;
    private double instructorGradeCoefficient;
    private double dailyHomeworkGradeCoefficient;
    private double behaviorInClassCoefficientPercentage ;
    private double courseInterestLevelCoefficientPercentage ;
    private double cameraOpeningGradeCoefficientPercentage ;
    private double instructorGradeCoefficientPercentage ;
    private double dailyHomeworkGradeCoefficientPercentage ;
}
