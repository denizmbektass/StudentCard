package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetDefaultTranscriptResponseDto {
    private int interviewPercentage;
    private int examPercentage;
    private int projectPercentage;
    private int instructorPercentageOfOpinion;
    private int homeworkPercentage;
    private int internshipSuccessRatePercentage;
}
