package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AverageProjectBehaviorRequestDto {

    private String projectBehaviorId;
    private Long rapportScore;
    private Long insterestScore;
    private Long presentationScore;
    private Long retroScore;
}
