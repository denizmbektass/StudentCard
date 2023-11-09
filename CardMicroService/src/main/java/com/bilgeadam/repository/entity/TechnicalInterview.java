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
public class TechnicalInterview extends BaseEntity {
    @Id
    private String technicalInterviewId;
    private String studentId;
    private Long directionCorrect;
    private Long completionTime;
    private Long levelReached;
    private Long supportTaken;
    private String comment;
    private String questionComment1;
    private String questionComment2;
    private Boolean questionComment3;
    private Double technicalInterviewAveragePoint;

    @Builder.Default()
    private EStatus eStatus = EStatus.ACTIVE;

}
