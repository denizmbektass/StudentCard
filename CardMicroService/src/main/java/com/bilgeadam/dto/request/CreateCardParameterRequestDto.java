package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardParameterRequestDto {
    private String groupName;
    private Map<String,Integer> parameters;
}
