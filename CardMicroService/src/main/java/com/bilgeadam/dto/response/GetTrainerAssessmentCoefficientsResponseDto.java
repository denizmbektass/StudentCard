package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTrainerAssessmentCoefficientsResponseDto {

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
    private double averageScore;
}
