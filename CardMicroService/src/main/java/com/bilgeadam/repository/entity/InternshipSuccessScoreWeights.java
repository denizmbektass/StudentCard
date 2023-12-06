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
public class InternshipSuccessScoreWeights extends BaseEntity {
    @Id
    public String internshipSuccessScoreWeightsId;
    public String groupName;
    public Double tasksWeight = 20.0;
    public Double teamworkWeight = 20.0;
    public Double personalMotivationWeight = 15.0;
    public Double contributionWeight = 10.0;
    public Double attendanceWeight = 10.0;
    public Double teamLeaderWeight = 25.0;
}
