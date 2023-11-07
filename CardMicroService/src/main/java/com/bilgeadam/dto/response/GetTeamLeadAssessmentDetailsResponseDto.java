package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTeamLeadAssessmentDetailsResponseDto {
    private Double score;
    private Double successScore;
    private String assessment;
}
