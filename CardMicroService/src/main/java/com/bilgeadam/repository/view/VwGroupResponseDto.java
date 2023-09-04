package com.bilgeadam.repository.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VwGroupResponseDto {
    private String internShipGroupId;
    private String groupName;
}
