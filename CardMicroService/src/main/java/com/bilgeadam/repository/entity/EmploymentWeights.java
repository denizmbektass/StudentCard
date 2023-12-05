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
public class EmploymentWeights extends BaseEntity{
    @Id
    public String employmentWeightsId;
    public String groupName;
    public Double documentSubmitWeight = 5.0;
    public Double careerEducationWeight = 35.0;
    public Double employmentInterviewWeight = 35.0;
    public Double applicationProcessWeight = 25.0;
}
