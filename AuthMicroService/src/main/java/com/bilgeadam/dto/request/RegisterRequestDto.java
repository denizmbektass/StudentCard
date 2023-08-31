package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @Email(message = "Please enter a valid e-mail address")
    private String email;
    private String name;
    private String userId;
    private String surname;
    @Builder.Default
    private List<String> role = new ArrayList<>();


}
