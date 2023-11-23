package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTeamworkResponseDto {
    private short communicationWithinTeamPoint;
    private short askingForHelpPoint;
    private short helpingTeamMembersPoint;
    private short communicationWithTeamLeadersPoint;
    private short projectPresentationPoint;
    private short projectOwnerMeetingActivityPoint;
}
