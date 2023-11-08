package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternshipSuccessResponseDto {
    Double teamLeadAssessmentSuccessScore = null;
    Double teamWorkSuccessScore = null;
    Double contributionSuccessScore = null;
    Double attendanceSuccessScore = null;
    Double personalMotivationSuccessScore = null ;
    Double tasksSuccessScore = null ;
    Double teamLeadAssessmentScore = null;
    Double teamWorkScore = null;
    Double contributionScore = null;
    Double attendanceScore = null;
    Double personalMotivationScore = null ;
    Double tasksScore = null ;
    Double totalSuccessScore = 0.0;
}
