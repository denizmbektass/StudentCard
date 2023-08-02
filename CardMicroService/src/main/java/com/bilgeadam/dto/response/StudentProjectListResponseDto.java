package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EProjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProjectListResponseDto {
    private String projectId;
    private EProjectType projectType;
    private Long projectScore;
    private String description;
    private String studentNameAndSurname;
}
