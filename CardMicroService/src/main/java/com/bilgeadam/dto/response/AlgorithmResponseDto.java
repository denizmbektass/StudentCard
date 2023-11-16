package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmResponseDto {
    private Double firstScore;
    private Double secondScore;
    private Double finalScore;
    private boolean isExempt;
}
