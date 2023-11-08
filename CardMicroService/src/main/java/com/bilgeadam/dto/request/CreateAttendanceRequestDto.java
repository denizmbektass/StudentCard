package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateAttendanceRequestDto {
    @NotNull
    String studentToken;
    @NotNull
    Double dailyScrum;
    @NotNull
    Double sprintReview;
    @NotNull
    Double sprintPlaning;
    @NotNull
    Double codeReview;
    @NotNull
    Double sprintRetrospective;
}
