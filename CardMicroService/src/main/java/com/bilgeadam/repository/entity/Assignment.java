package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.AssignmentType;
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
public class Assignment extends BaseEntity{
    @Id
    private String assignmentId;
    private String title;
    private AssignmentType type;
    private Long score;
    private String statement;
    private String studentId;
}
