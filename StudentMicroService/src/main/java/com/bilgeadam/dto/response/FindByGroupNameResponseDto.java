package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindByGroupNameResponseDto {

    private String studentId;
    private String name;
    private String surname;
    private String email;
    private List<ERole> roleList;
    private List<String> groupNameList;

}
