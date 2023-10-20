package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
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

    //private String projectId;
    private String studentId;
    private Long rapportScore;
    private Long insterestScore;
    private Long presentationScore;
    private Long retroScore;
    private Long totalScore;

}
