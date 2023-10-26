package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerAssessmentSaveRequestDto {

    private String assessmentName;
    private double behaviorInClass;
    private double courseInterestLevel;
    private double cameraOpeningGrade;
    private double instructorGrade;
    private double dailyHomeworkGrade;
    private String description;
    private String studentToken;
}
