package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.EProjectType;
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
    private String token;
    private EProjectType projectType;
    private Long projectScore;
    private String description;
}
