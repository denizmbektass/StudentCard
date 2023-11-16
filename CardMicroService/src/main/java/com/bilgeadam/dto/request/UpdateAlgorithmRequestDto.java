package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAlgorithmRequestDto {
    private String studentToken;
    private Double firstScore;
    private Double secondScore;
    private boolean isExempt;
}
