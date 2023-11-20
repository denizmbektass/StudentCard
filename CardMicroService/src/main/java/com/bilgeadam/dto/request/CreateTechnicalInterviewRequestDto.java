package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTechnicalInterviewRequestDto {

    private String studentToken;
    private Long directionCorrect;
    private Long completionTime;
    private Long levelReached;
    private Long supportTaken;
    private String comment;
    private String completionTimeComment;
    private String levelReachedComment;
    private Boolean supportTakenChoice;
    private boolean isExempt;

}
