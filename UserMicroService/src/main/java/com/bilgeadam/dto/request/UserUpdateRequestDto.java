package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String id;
    private String name;
    private String surname;
    private Long identityNumber;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String birthPlace;
    private String school;
    private String department;
}
