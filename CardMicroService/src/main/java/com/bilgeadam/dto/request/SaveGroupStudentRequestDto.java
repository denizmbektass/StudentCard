package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
}
