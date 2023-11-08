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
public class PersonalMotivation extends BaseEntity {

    @Id
    private String personalMotivationId;
    private String studentId;
    private Long camera;
    private Long backlog;
    private Long clothes;
    private Long participation;
    private Long workingEnvironment;
    private Double average;
    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;
    private Long cameraPercentage;
    private Long backlogPercentage;
    private Long clothesPercentage;
    private Long participationPercentage;
    private Long workingEnvironmentPercentage;
}
