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
public class User extends BaseEntity {
    @Id
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
    @Builder.Default
    private EStatus status = EStatus.ACTIVE;
    private String profilePicture;
}
