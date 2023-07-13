package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String id;
    @NotBlank(message = "Name can not blank")
    private String name;
    @NotBlank(message = "Surname can not blank")
    private String surname;
    @NotBlank(message = "Identity number can not blank")
    private Long identityNumber;
    @NotBlank(message = "Phone can not blank")
    @Pattern(regexp = "^(\\d{3}[- .]?){2}\\d{4}$")
    private String phoneNumber;
    @NotBlank(message = "Address can not be blank")
    private String address;
    @NotBlank(message = "Birthdate can not be blank")
    private LocalDate birthDate;
    @NotBlank(message = "Birthplace can not be blank")
    private String birthPlace;
    @NotBlank(message = "School name can not be blank")
    private String school;
    @NotBlank(message = "Department can not be blank")
    private String department;
}
