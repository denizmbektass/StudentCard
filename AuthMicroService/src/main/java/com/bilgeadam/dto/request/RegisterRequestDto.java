package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @Email(message = "Please enter a valid e-mail address")
    private String email;
    @NotBlank(message = "Please enter your name")
    @Length(min = 3, max = 32, message = "İsim minimum 3, maximum 32 karekter olmalıdır")
    private String name;
    private String userId;
    @NotBlank(message = "Please enter your surname")
    @Length(min = 3, max = 32, message = "Soyisim minimum 3, maximum 32 karekter olmalıdır")
    private String surname;
    @Builder.Default
    private List<String> role = new ArrayList<>();


}
