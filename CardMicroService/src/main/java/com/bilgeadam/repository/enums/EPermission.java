package com.bilgeadam.repository.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EPermission {
    ADMIN_READ("read"),
    ADMIN_WRITE("admin:write"),
    ASSISTANT_TRAINER_READ("read"),
    ASSISTANT_TRAINER_WRITE("assistant_trainer:write"),
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    MASTER_TRAINER_READ("read"),
    MASTER_TRAINER_WRITE("master_trainer:write"),
    WORKSHOP_TEAM_READ("read"),
    WORKSHOP_TEAM_WRITE("workshop_team:write"),
    INTERNSHIP_TEAM_READ("read"),
    INTERNSHIP_TEAM_WRITE("internship_team:write"),
    EMPLOYMENT_TEAM_READ("read"),
    EMPLOYMENT_TEAM_WRITE("employment_team:write")

    ;

    @Getter
    private final String permission;
}