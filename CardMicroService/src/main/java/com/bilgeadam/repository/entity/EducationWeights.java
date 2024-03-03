package com.bilgeadam.repository.entity;

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
public class EducationWeights extends BaseEntity{
    @Id
    public String educationWeightsId;
    public String groupName;
    public Double examWeight = 15.0;
    public Double projectBehaviorWeight = 10.0;
    public Double assessmentWeight = 10.0;
    public Double assignmentWeight = 10.0;
    public Double oralExamWeight = 10.0;
    public Double obligationWeight = 25.0;
    public Double graduationProjectWeight = 20.0;
}
