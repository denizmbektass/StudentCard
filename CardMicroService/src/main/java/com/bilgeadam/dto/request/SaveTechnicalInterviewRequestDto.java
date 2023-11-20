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
public class SaveTechnicalInterviewRequestDto {
    @NotNull
    private String studentToken;
    @Min(0)
    @Max(100)
    private Long directionCorrect;
    @Min(0)
    @Max(100)
    private Long completionTime;
    @Min(0)
    @Max(100)
    private Long levelReached;
    @Min(0)
    @Max(100)
    private Long supportTaken;
    private String completionTimeComment;
    private String levelReachedComment;
    private Boolean supportTakenChoice;
    private String comment;
    private boolean isExempt;





}
