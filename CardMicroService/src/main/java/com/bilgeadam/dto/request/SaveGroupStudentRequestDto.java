package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveGroupStudentRequestDto {
    @NotBlank(message = "Grup İsmi Eksik Girilmiştir")
    private String groupName;
    @NotBlank(message = "İsim Eksik Girilmiştir")
    private String name;
    @NotBlank(message = "Soyisim Eksik Girismiştir")
    private String surname;
    @NotNull(message = "Öğrenci Id'si Eksik Girilmiştir")
    private String userId;
}
