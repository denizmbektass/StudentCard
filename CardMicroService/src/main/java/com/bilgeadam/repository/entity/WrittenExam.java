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
public class WrittenExam extends BaseEntity{
    @Id
    String writtenExamId;
    int correctAnswers;
    Double score;
    String studentId;
}
