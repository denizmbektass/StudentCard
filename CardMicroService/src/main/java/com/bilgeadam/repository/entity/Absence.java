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
    String absenceId;
    String userId;
    String group;
    String groupName;
    int absenceDateTheo;
    int hourOfAbsenceTheo;
    int totalCourseHoursTheo;
    int hourOfAbsenceLimitTheo;
    int absenceDatePrac;
    int hourOfAbsencePrac;
    int totalCourseHoursPrac;
    int hourOfAbsenceLimitPrac;
}
