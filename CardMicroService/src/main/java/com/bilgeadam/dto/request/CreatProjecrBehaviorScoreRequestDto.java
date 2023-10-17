package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatProjecrBehaviorScoreRequestDto {

    private String token;
    private String studentId;
    private Long rapportScore;
    private Long insterestScore;
    private Long presentationScore;
    private Long retroScore;
}
