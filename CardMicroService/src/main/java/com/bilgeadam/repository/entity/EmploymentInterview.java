package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class EmploymentInterview extends BaseEntity{
    @Id
    private String employmentInterviewId;
    private String studentId;
    private Double hrInterviewScore;
    private Double hrInterviewFinalScore;
    private String hrInterviewComment;
    private Double technicalInterviewScore;
    private Double technicalInterviewFinalScore;
    private Double totalScore;
    private String technicalInterviewComment;
    @Builder.Default()
    private EStatus eStatus = EStatus.ACTIVE;
}
