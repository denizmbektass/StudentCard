package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetEmploymentInterviewResponseDto {
    private double hrInterviewScore;
    private double hrInterviewFinalScore;
    private String hrInterviewComment;
    private double technicalInterviewScore;
    private double technicalInterviewFinalScore;
    private String technicalInterviewComment;
}
