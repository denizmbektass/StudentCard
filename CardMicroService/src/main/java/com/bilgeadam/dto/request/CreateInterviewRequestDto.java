package com.bilgeadam.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewRequestDto {
    private String name;
    private String token;
    private long score;
    private String description;
    private String interviewType;
}
