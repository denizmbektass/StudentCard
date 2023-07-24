package com.bilgeadam.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewRequestDto {
    private String name;
    private long score;
    private String description;
    private String studentId;
    private String interviewType;
}