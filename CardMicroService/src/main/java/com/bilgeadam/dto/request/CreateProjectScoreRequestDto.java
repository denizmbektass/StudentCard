package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectScoreRequestDto {
    private String userId;
    private Long intermediateProjectScore;
    private Long mainProjectScore;
    private Long graduationProjectScore;
}
