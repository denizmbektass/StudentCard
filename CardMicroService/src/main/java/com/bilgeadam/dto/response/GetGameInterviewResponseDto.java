package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GetGameInterviewResponseDto {

    private Long directionCorrect;
    private Long completionTime;
    private Long levelReached;
    private Long supportTaken;
    private String comment;
    private Double gameInterviewAveragePoint;
    private String questionComment1;
    private String questionComment2;
    private Boolean questionComment3;
}
