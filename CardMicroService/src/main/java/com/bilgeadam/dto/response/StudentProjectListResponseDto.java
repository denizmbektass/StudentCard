package com.bilgeadam.dto.response;

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
    private String projectType;
    private Long projectScore;
    private String description;
    private String studentNameAndSurname;
}
