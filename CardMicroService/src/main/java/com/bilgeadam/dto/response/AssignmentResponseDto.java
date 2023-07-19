package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponseDto {
    private String assignmentType;
    private String title;
    private Long score;
    private String statement;
}
