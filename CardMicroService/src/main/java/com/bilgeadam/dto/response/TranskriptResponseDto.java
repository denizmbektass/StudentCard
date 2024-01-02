package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TranskriptResponseDto {
    String name_surname;
    String product_information;
    Double writtenExamScore = null;
    Double candidateInterviewScore = null;
    Double algorithmScore = null;
    Double technicalInterviewScore = null;
    Double studentChoiceTotalSuccessScore = 0.0;;
    Double assignmentSuccessScore = null;
    Double examSuccessScore = null;
    Double trainerAssessmentSuccessScore = null;
    Double projectSuccessScore = null;
    Double absencePerformSuccessScore = null;
    Double graduationProjectSuccessScore = null;
    Double educationDetailsTotalSuccessScore = 0.0;
    Double teamLeadAssessmentSuccessScore = null;
    Double teamWorkSuccessScore = null;
    Double contributionSuccessScore = null;
    Double attendanceSuccessScore = null;
    Double personalMotivationSuccessScore = null ;
    Double tasksSuccessScore = null ;
    Double internshipSuccessTotalSuccessScore = 0.0;
    Double careerEducationSuccessScore = null;
    Double documentSumbitSuccessScore = null;
    Double applicationProcessSuccessScore = null;
    Double employmentInterviewSuccessScore = null;
    Double employmentScoreDetailsTotalSuccessScore = 0.0;
}
