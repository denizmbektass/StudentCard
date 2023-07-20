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
public class TrainerAssessment extends BaseEntity{
    @Id
    private String trainerAssessmentId;
    private Long score;
    private String description;
    private String studentId;
    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
}
