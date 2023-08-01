package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EProjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectScoreResponseDto {
    private EProjectType projectType;
    private Long projectScore;
    private String description;
    private String name;
    private String surname;
}
