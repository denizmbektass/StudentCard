package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentAndTrainerRequestDto {
    private String email;
    private String name;
    private String userId;
    private String surname;
    @Builder.Default
    private List<String> role = new ArrayList<>();
}
