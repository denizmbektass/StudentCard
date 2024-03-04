package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OralExamRequestDto {
    @NotNull
    private String studentToken;
    @NotBlank(message = "Bu kısım boş bırakılamaz")
    private String title;
    @NotBlank(message = "Bu kısım boş bırakılamaz")
    private String statement;
    @NotNull(message = "Bu kısım boş bırakılamaz")
    private Long score;
}
