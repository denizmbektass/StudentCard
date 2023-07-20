package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectScoreResponseDto {
    private Long intermediateProjectScore;
    private Long mainProjectScore;
    private Long graduationProjectScore;
}
