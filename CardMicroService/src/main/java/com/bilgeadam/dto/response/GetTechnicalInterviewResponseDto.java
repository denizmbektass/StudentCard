package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GetTechnicalInterviewResponseDto {

    private Long directionCorrect;
    private Long completionTime;
    private Long levelReached;
    private Long supportTaken;
    private String comment;
    private Double technicalInterviewAveragePoint;
    private String questionComment1;
    private String questionComment2;
    private Boolean questionComment3;
}