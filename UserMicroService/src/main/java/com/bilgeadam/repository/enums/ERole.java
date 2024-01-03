package com.bilgeadam.repository.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bilgeadam.repository.enums.EPermission.*;

@RequiredArgsConstructor
public enum ERole {
    ADMIN(
            Set.of(
                    ADMIN_WRITE,
                    ADMIN_READ,
                    ASSISTANT_TRAINER_READ,
                    ASSISTANT_TRAINER_WRITE,
                    STUDENT_READ,
                    STUDENT_WRITE,
                    CANDIDATE_READ,
                    CANDIDATE_WRITE,
                    MASTER_TRAINER_READ,
                    MASTER_TRAINER_WRITE,
                    WORKSHOP_TEAM_READ,
                    WORKSHOP_TEAM_WRITE,
                    INTERNSHIP_TEAM_READ,
                    INTERNSHIP_TEAM_WRITE,
                    EMPLOYMENT_TEAM_READ,
                    EMPLOYMENT_TEAM_WRITE
            )
    ),ASSISTANT_TRAINER(
            Set.of(
                    ASSISTANT_TRAINER_READ,
                    ASSISTANT_TRAINER_WRITE
            )
    ),STUDENT(
            Set.of(
                    STUDENT_READ,
                    STUDENT_WRITE
            )
    ), CANDIDATE(
            Set.of(
                    CANDIDATE_READ,
                    CANDIDATE_WRITE
            )
    ),MASTER_TRAINER(
            Set.of(
                    MASTER_TRAINER_READ,
                    MASTER_TRAINER_WRITE
            )
    ),WORKSHOP_TEAM(
            Set.of(
                    WORKSHOP_TEAM_READ,
                    WORKSHOP_TEAM_WRITE
            )
    ),INTERNSHIP_TEAM(
            Set.of(
                    INTERNSHIP_TEAM_READ,
                    INTERNSHIP_TEAM_WRITE
            )
    ),EMPLOYMENT_TEAM(
            Set.of(
                    EMPLOYMENT_TEAM_READ,
                    EMPLOYMENT_TEAM_WRITE
            )
    );

    @Getter
    private final Set<EPermission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
