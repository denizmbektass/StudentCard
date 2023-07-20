package com.bilgeadam.dto.response;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewResponseDto {
    private String name;
    private long score;
    private String description;
    private String studentId;
    private String interviewType;

}
