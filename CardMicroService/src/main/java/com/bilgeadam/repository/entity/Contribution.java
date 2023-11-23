package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Contribution extends BaseEntity {

    @Id
    private String contributionId;

    private String studentId;
    private Double incorrectCodeOrDisplayMessageNote;
    private Double documentationForBacklogNote;
    private Double researchNote;
    private Double intraTeamTrainingNote;
    private Double totalScoreContribution;
}
