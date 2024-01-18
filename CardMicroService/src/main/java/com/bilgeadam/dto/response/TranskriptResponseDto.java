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
  Double writtenExamScore;
  Double candidateInterviewScore;
  Double algorithmScore;
  Double technicalInterviewScore;
  Double studentChoiceTotalSuccessScore;
  Double assignmentSuccessScore;
  Double examSuccessScore;
  Double trainerAssessmentSuccessScore;
  Double projectSuccessScore;
  Double absencePerformSuccessScore;
  Double graduationProjectSuccessScore;
  Double educationDetailsTotalSuccessScore;
  Double teamLeadAssessmentSuccessScore;
  Double teamWorkSuccessScore;
  Double contributionSuccessScore;
  Double attendanceSuccessScore;
  Double personalMotivationSuccessScore;
  Double tasksSuccessScore;
  Double internshipSuccessTotalSuccessScore;
  Double careerEducationSuccessScore;
  Double documentSumbitSuccessScore;
  Double applicationProcessSuccessScore;
  Double employmentInterviewSuccessScore;
  Double employmentScoreDetailsTotalSuccessScore;
  Double generalBoostAchievementPoints;
}
