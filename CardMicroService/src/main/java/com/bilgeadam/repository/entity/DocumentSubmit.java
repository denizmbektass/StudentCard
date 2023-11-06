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
public class DocumentSubmit extends BaseEntity {
    @Id
    String documentSubmitId;
    String studentId;
    Double documentSubmitAverageScore;
    Integer documentSubmitScore;
}
