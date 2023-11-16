package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAlgorithmRequestDto {
    @NotNull
    private String studentToken;
    private Double firstScore;
    private Double secondScore;
    private boolean isExempt;
}
