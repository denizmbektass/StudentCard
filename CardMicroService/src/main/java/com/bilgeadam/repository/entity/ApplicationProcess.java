package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
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
public class ApplicationProcess extends BaseEntity {
    @Id
    private String applicationProcessId;
    private String studentId;
    private int jobApplicationScore;
    private int informationSharingScore;
    private int referralParticipationScore;
    private int companyFitScore;
    private int careerTeamAssessment;
    private double totalScore;
}
