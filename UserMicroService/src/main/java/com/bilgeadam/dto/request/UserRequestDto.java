package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String name;
    private String surname;
    private Long identityNumber;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String birthPlace;
    private String school;
    private String department;
    private String email;
    private List<String> groupNameList;
    private List<ERole> roleList;
    private Long rowNumber;
    private LocalDate applicationDate;
    private String education;
    private String educationStatus;
    private String className;
    private String englishLevel;
    private String city;
    private String district;
    private String educationBranch;
    private String relevantBranch;
    private LocalDate workshopDate;
    private String workshopTime;
    private String workshopPlace;
    private String participationStatus;
    private String examStatus;
    private LocalDate interviewDate;
    private String interviewParticipationStatus;
    private String interviewer;
    private String evaluation;
    private String examAndInterviewResult;
    private String contract;
    private String notes;
    private String channel;
}
