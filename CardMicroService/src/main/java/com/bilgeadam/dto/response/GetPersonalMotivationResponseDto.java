package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPersonalMotivationResponseDto {
    private Long camera;
    private Long backlog;
    private Long clothes;
    private Long participation;
    private Long workingEnvironment;
    private Double average;
    private Long cameraPercentage;
    private Long backlogPercentage;
    private Long clothesPercentage;
    private Long participationPercentage;
    private Long workingEnvironmentPercentage;
}
