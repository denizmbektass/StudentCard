package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
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
}
