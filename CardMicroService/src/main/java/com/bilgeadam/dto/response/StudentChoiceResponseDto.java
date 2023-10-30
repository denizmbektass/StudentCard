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
    double writtenExamSuccessScore;
    double algorithmSuccessScore;
    double candidateInterviewSuccessScore;
    double gameInterviewSuccessScore;
    double writtenExamScore;
    double candidateInterviewScore;
    double algorithmScore;
    double gameInterviewScore;
    double totalSuccessScore;
}
