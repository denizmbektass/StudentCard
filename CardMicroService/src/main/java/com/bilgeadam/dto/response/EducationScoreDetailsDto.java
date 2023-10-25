package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationScoreDetailsDto {
    double averageAssignmentScore;
    double averageExamScore;
    double averageTrainerAssessmentScore;
    double averageProjectScore;
    double averageAbsencePerformScore;
    double averageGraduationProjectScore;
    double assignmentSuccessScore;
    double examSuccessScore;
    double trainerAssessmentSuccessScore;
    double projectSuccessScore;
    double absencePerformSuccessScore;
    double graduationProjectSuccessScore;
    double totalSuccessScore;
}
