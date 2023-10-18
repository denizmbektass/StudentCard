package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewForTranscriptResponseDto {
    private String name;
    private Long score;
    private String description;
    private String interviewType;
}
