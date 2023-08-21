package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class GroupAttendance extends BaseEntity{
    @Id
    private String groupAbsenceId;
    private Date attendanceDate;
    private String groupId;
    private Map<String,Boolean> groupStudents;
}
