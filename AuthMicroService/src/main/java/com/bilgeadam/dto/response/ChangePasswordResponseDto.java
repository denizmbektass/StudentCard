package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordResponseDto {
String userId;
@Pattern( message = "Password requires to be with at least eight characters." +
        " It must includes at least one lower, one upper, one special character and a number",
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!.,?])(?=\\S+$).{8,}$")
String newPassword;
}
