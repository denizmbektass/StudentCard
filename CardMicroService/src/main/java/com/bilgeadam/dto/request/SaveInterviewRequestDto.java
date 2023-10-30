package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveInterviewRequestDto {
    @NotEmpty
    private String studentToken;
    @Min(0)
    @Max(100)
    @NotNull
    private short communicationSkillsPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short workExperiencePoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short universityPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short universityProgramPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short agePoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short personalityEvaluationPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short englishLevelPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short graduationPeriodPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short militaryServicePoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short motivationPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short residencyPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short softwareEducationPoint;
}
