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
public class TranskriptDefault extends BaseEntity{
@Id
private String transkriptDefaultId;
private String mainGroupName;
private int interviewPercentage;
private int examPercentage;
private int projectPercentage;
private int instructorPercentageOfOpinion;
private int homeworkPercentage;
private int internshipSuccessRatePercentage;

}
