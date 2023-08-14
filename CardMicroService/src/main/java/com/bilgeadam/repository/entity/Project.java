package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EProjectType;
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
public class Project extends BaseEntity{
    @Id
    private String projectId;
    private String title;
    private EProjectType projectType;
    private Long projectScore;
    private String description;
    private String userId;
    @Builder.Default
    private EStatus status = EStatus.ACTIVE;
}
