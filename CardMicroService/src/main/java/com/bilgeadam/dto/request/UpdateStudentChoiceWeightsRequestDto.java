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
public class UpdateStudentChoiceWeightsRequestDto {
    @NotNull
    public String studentChoiceWeightsId;
    @NotNull
    private String groupName;
    @Min(0)
    @Max(100)
    @NotNull
    private Double writtenExamWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double candidateInterviewWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double algorithmWeight;
    @Min(0)
    @Max(100)
    @NotNull
    private Double technicalInterviewWeight;
}
