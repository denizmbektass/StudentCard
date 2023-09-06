package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptDefaultRequestDto {
    private String mainGroupName;
    private int interviewPercentage;
    private int examPercentage;
    private int projectPercentage;
    private int instructorPercentageOfOpinion;
    private int homeworkPercentage;
    private int internshipSuccessRatePercentage;
}
