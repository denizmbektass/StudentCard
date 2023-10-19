package com.bilgeadam.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainerAssessmentScoreCalculateRequestDto {

    private String trainerAssessmentId;
    private String assessmentName;
    private double behaviorInClass;
    private double courseInterestLevel;
    private double cameraOpeningGrade;
    private double instructorGrade;
    private double dailyHomeworkGrade;
    private double totalTrainerAssessmentScore;
    private String description;

}
