package com.bilgeadam.dto.request;

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
public class CreateGameInterviewRequestDto {

    private String studentToken;
    private Long directionCorrect;
    private Long completionTime;
    private Long levelReached;
    private Long supportTaken;
    private String comment;
    private String questionComment1;
    private String questionComment2;
    private Boolean questionComment3;

}
