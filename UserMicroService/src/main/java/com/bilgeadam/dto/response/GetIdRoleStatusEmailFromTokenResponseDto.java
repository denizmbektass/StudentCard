package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIdRoleStatusEmailFromTokenResponseDto {
    String id;
    List<String> role;
    EStatus status;
    String email;
}
