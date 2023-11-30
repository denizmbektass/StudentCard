package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMainWeightsRequestDto {
    @NotNull
    private String groupName;
    @Min(0)
    @Max(100)
    @NotNull
    private Double studentChoiceWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double educationWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double internshipSuccessWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double employmentWeight;
}
