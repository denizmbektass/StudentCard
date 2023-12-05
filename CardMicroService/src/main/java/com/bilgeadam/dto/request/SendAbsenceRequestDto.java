package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SendAbsenceRequestDto {
    private String absenceId;
    private int hourOfAbsence;
    private String userId;
    private String group;
    private String groupName;
    private int absenceDate;
    private int totalCourseHours;
    int hourOfAbsenceLimit;
}
