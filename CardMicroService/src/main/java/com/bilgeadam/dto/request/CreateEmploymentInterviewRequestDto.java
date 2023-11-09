package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmploymentInterviewRequestDto {
    @NotNull
    private String studentToken;
    @Min(0)
    @Max(100)
    private Double hrInterviewScore;
    @Min(0)
    @Max(100)
    private Double technicalInterviewScore;
    private String hrInterviewComment;
    private String technicalInterviewComment;
}
