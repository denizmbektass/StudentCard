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
public class GameInterview extends BaseEntity {
    @Id
    private String gameInterviewId;
    private String studentId;
    private Long directionCorrect;
    private Long completionTime;
    private Long levelReached;
    private Long supportTaken;
    private String comment;
    private Double gameInterviewAveragePoint;
    @Builder.Default()
    private EStatus eStatus = EStatus.ACTIVE;

}
