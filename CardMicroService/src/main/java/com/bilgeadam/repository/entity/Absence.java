package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Absence extends BaseEntity {
    @Id
    private String absenceId;
    private byte hourOfAbsence;
    private String userId;
    private String group;
    private String groupName;
    private Long absenceDate;
    private short totalCourseHours;
    int hourOfAbsenceLimit;
}
