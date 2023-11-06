package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentScoreDetailsDto {
    Double careerEducationSuccessScore = null;
    Double documentSumbitSuccessScore = null;
    Double applicationProcessSuccessScore = null;
    Double employmentInterviewSuccessScore = null;
    Double totalSuccessScore = 0.0;
}
