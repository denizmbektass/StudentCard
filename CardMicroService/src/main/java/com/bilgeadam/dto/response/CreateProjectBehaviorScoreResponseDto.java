package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectBehaviorScoreResponseDto {

    private Long rapportScore;
    private Long insterestScore;
    private Long presentationScore;
    private Long retroScore;
}
