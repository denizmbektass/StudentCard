package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Teamwork extends BaseEntity {
    @Id
    private String teamworkId;
    private String studentId;
    private short communicationWithinTeamPoint;
    private short askingForHelpPoint;
    private short helpingTeamMembersPoint;
    private short communicationWithTeamLeadersPoint;
    private short projectPresentationPoint;
    private short projectOwnerMeetingActivityPoint;

}
