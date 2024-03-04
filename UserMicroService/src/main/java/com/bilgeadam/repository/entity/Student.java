package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Student extends BaseEntity {

    @Id
    private String studentId;
    private Long rowNumber;
    private String name;
    private String surname;
    private String channel;
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
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String birthPlace;
    private String school;
    private String department;
    private String email;
    private List<String> groupNameList;
    private List<ERole> roleList;
    @Builder.Default
    private EStatus status = EStatus.ACTIVE;
    private String profilePicture;
    private Double egitimSaati;
    private EStatus internShipStatus = EStatus.PASSIVE;

}
