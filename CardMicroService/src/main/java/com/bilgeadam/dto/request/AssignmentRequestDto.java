package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.TextScore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AssignmentRequestDto {
    @NotNull
    private String studentToken;
    @NotBlank(message = "Bu kısım boş bırakılamaz")
    private String title;
    @NotBlank(message = "Bu kısım boş bırakılamaz")
    private String statement;
    @NotNull(message = "Bu kısım boş bırakılamaz")
    private Long score;
}
