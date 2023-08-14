package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDto {

    @NotBlank(message = "email can not be blank")
    private String email;
    @NotBlank(message = "Password can not be blank")
    private String password;
    @NotBlank(message = "Repassword can not be blank")
    private String rePassword;
}
