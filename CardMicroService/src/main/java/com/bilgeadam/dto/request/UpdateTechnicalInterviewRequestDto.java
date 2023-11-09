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
    @Min(0)
    @Max(200)
    @NotNull
    private String questionComment1;
    @Min(0)
    @Max(200)
    @NotNull
    private String questionComment2;
    @NotNull
    private Boolean questionComment3;
    private String comment;

}
