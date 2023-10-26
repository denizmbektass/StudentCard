package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCandidateInterviewResponseDto {
    private short communicationSkillsPoint;
    private short workExperiencePoint;
    private short universityPoint;
    private short universityProgramPoint;
    private short agePoint;
    private short personalityEvaluationPoint;
    private short englishLevelPoint;
    private short graduationPeriodPoint;
    private short militaryServicePoint;
    private short motivationPoint;
    private short residencyPoint;
    private short softwareEducationPoint;

}
