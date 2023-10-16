package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowUserAbsenceInformationResponseDto {
    private Double group1Percentage;
    private Double group2Percentage;
    private String groupName;
    private int group1AbsenceNumber;
    private int group2AbsenceNumber;

}
