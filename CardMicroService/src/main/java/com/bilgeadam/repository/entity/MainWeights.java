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
public class MainWeights extends BaseEntity {
    @Id
    public String mainWeightsId;
    public String groupName;
    public Double studentChoiceWeight = 0.0;
    public Double educationWeight = 0.0;
    public Double internshipSuccessWeight = 0.0;
    public Double employmentWeight = 0.0;
    public Double totalWeight;
}
