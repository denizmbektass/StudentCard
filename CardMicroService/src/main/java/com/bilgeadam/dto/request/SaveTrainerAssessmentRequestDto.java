package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveTrainerAssessmentRequestDto {
    @NotNull
    private String studentToken;
    @Min(0)
    @Max(100)
    @NotNull
    private Double behaviorInClassCoefficient;
    @Min(0)
    @Max(100)
    @NotNull
    private Double courseInterestLevelCoefficient;
    @Min(0)
    @Max(100)
    @NotNull
    private Double cameraOpeningGradeCoefficient;
    @Min(0)
    @Max(100)
    @NotNull
    private Double instructorGradeCoefficient;
    @Min(0)
    @Max(100)
    @NotNull
    private Double dailyHomeworkGradeCoefficient;
    @Min(0)
    @Max(100)
    @NotNull
    private Double behaviorInClassCoefficientPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double courseInterestLevelCoefficientPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double cameraOpeningGradeCoefficientPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double instructorGradeCoefficientPercentage ;
    @Min(0)
    @Max(100)
    @NotNull
    private Double dailyHomeworkGradeCoefficientPercentage ;
}
