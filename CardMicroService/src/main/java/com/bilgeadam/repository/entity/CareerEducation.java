package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class CareerEducation extends BaseEntity {
    @Id
    private String careerEducationId;
    private String studentId;
    private short centralHrContinuityPoint;
    private short centralHrActivityPoint;
    private short boostContinuityPoint;
    private short boostActivityPoint;
    private short resumeDeliveryPoint;
    private short resumeConveniencePoint;
    private short resumeUpToDatePoint;

}
