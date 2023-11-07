package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Document
public class Attendance extends BaseEntity{
    @Id
    String attendanceId;
    String studentId;
    Double dailyScrum;
    Double sprintReview;
    Double sprintPlaning;
    Double codeReview;
    Double sprintRetrospective;
    Double attendanceAverage;
}
