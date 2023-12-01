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
public class StudentChoiceWeights extends BaseEntity {
    @Id
    public String studentChoiceWeightsId;
    public String groupName;
    public Double writtenExamWeight = 0.0;
    public Double candidateInterviewWeight = 0.0;
    public Double algorithmWeight = 0.0;
    public Double technicalInterviewWeight = 0.0;
}
