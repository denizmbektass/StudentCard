package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Interview extends BaseEntity{
    @Id
    private String interviewId;
    private String studentId;
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
    @Builder.Default()
    private EStatus eStatus = EStatus.ACTIVE;
    //private int trainerId,hrId;

}
