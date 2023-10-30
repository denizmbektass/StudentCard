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
    Double gameInterviewSuccessScore;
    Double writtenExamScore;
    Double candidateInterviewScore;
    Double algorithmScore;
    Double gameInterviewScore;
    Double totalSuccessScore;
}
