package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindStudentProfileResponseDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private LocalDate birthDate;
    private String birthPlace;
    private String school;
    private String department;
    private List<String> groupNameList;
    private String email;
    private String address;
    private String profilePicture;


}
