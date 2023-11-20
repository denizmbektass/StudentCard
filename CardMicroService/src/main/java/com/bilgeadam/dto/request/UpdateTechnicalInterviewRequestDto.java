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

public class UpdateTechnicalInterviewRequestDto {

    @NotNull
    private String studentToken;
    @Min(0)
    @Max(100)
    @NotNull
    private Long directionCorrect;
    @Min(0)
    @Max(100)
    @NotNull
    private Long completionTime;
    @Min(0)
    @Max(100)
    @NotNull
    private Long levelReached;
    @Min(0)
    @Max(100)
    @NotNull
    private Long supportTaken;
    private String completionTimeComment;
    private String levelReachedComment;
    private Boolean supportTakenChoice;
    private String comment;
    private boolean isExempt;

}
