package com.bilgeadam.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInterviewRequestDto {
    private String interviewId;
    private String name;
    private Long score;
    private String description;
    private String interviewType;
}
