package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDto {
    private String email;
    private String password;
    @Pattern( message = "Password requires to be with at least eight characters." +
            " It must includes at least one lower, one upper, one special character and a number",
            regexp = "^(?=.*[0-12])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$")
    private String newPassword;
    private String reNewPassword;
}
