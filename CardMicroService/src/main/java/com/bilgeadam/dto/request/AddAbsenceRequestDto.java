package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAbsenceRequestDto {
    private byte hourOfAbsence;
    private String userId;
    private Long absenceDate;
    private String group;
    private String groupName;
}
