package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAssignmentRequestDto {
    private String assignmentId;
    private String title;
    private String type;
    private Long score;
    private String statement;
}
