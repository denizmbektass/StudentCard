package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateGroupStudentAttendanceRequestDto {
    private Date attendanceDate;
    private String groupId;
    private Map<String,Boolean> groupStudents;
}
