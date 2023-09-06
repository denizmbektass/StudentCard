package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptDefaultResponseDto {
    private String mainGroupName;
    private int interviewPercentage;
    private int examPercentage;
    private int projectPercentage;
    private int instructorPercentageOfOpinion;
    private int homeworkPercentage;
    private int internshipSuccessRatePercentage;
}
