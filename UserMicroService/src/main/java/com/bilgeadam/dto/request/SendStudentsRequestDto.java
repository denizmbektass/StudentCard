package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendStudentsRequestDto {
    private String userId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private String school;
    private String department;
    private LocalDate birthDate;
    private String birthPlace;
    private EStatus status;
    private EStatus internShipStatus;
    private List<String> groupNameList;
    private LocalDate saleDate;
    private Long updateDate;
}
