package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProjectBehavior extends BaseEntity {

    @Id
    private String projectBehaviorId;
    private String studentId;
    private Long rapportScore;
    private Long interestScore;
    private Long presentationScore;
    private Long retroScore;
    private Double averageScore;
    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
    private Long rapportScorePercentage ;
    private Long interestScorePercentage ;
    private Long presentationScorePercentage ;
    private Long retroScorePercentage ;


}
