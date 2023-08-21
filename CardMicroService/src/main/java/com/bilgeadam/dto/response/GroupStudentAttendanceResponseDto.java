package com.bilgeadam.dto.response;

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
public class GroupStudentAttendanceResponseDto {
    private Date attendanceDate;
    private String groupName;
    private Map<String,Boolean> groupStudents;
}
