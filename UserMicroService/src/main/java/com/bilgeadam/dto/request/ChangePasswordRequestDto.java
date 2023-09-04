package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequestDto {
    @NotBlank(message = "please enter your last password")
    String lastPassword;
    @NotBlank(message = "enter your new password")
    String newPassword;
    @NotBlank(message = "enter your new password")
    String reNewPassword;

}
