package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {
    private Map<String,Integer> notes;
    private Integer totalNote;
}
