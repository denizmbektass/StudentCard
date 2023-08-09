package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamRequestDto {
    @NotBlank(message = "Bu kısım boş bırakılamaz")
    private String title;
    @NotNull(message = "Bu kısım boş bırakılamaz")
    private Long score;
    @NotBlank(message = "Bu kısım boş bırakılamaz")
    private String statement;
    @NotNull
    private String studentToken;

}
