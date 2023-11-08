package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePersonalMotivationRequestDto {

    private String studentToken;
    private Long camera;
    private Long backlog;
    private Long clothes;
    private Long participation;
    private Long workingEnvironment;
    private Long cameraPercentage;
    private Long backlogPercentage;
    private Long clothesPercentage;
    private Long participationPercentage;
    private Long workingEnvironmentPercentage;
}
