package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsMailReminderDto {
    private String studentId;
    private List<String> groupName;
    private Double egitimSaati;
    private String name;
    private String surname;
}
