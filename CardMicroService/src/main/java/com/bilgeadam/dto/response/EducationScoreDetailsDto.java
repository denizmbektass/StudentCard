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
    Double averageAssignmentScore;
    Double averageExamScore;
    Double averageTrainerAssessmentScore;
    Double averageProjectScore;
    Double averageAbsencePerformScore;
    Double averageGraduationProjectScore;
    Double assignmentSuccessScore;
    Double examSuccessScore;
    Double trainerAssessmentSuccessScore;
    Double projectSuccessScore;
    Double absencePerformSuccessScore;
    Double graduationProjectSuccessScore;
    Double totalSuccessScore;
}
