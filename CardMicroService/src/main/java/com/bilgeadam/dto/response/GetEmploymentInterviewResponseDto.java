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
    private Double hrInterviewScore;
    private Double hrInterviewFinalScore;
    private String hrInterviewComment;
    private Double technicalInterviewScore;
    private Double technicalInterviewFinalScore;
    private Double totalScore;
    private String technicalInterviewComment;
}
