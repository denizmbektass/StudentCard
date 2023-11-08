package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetAttendanceResponseDto {
    Double dailyScrum;
    Double sprintReview;
    Double sprintPlaning;
    Double codeReview;
    Double sprintRetrospective;
    Double attendanceAverage;
}
