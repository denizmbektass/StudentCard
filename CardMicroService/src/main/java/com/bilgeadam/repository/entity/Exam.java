package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Exam extends BaseEntity{
    @Id
    private String examId;
    private String title;
    private Long score;
    private String statement;
    private String studentId;
    private List<String> groupNames;
    private double averageScore;
}
