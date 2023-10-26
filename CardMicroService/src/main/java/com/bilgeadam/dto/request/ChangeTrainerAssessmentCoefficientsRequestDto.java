package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTrainerAssessmentCoefficientsRequestDto {

    private double behaviorInClassCoefficient;
    private double courseInterestLevelCoefficient;
    private double cameraOpeningGradeCoefficient;
    private double instructorGradeCoefficient;
    private double dailyHomeworkGradeCoefficient;
}
