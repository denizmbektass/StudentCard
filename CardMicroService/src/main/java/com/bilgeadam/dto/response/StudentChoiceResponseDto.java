package com.bilgeadam.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentChoiceResponseDto {
    Double writtenExamSuccessScore;
    Double algorithmSuccessScore;
    Double candidateInterviewSuccessScore;
    Double technicalInterviewSuccessScore;
    Double writtenExamScore;
    Double candidateInterviewScore;
    Double algorithmScore;
    Double technicalInterviewScore;
    Double totalSuccessScore;
    Boolean isExemptFromAlgorithm;
    Boolean isExemptFromTechnicalInterview;
}
