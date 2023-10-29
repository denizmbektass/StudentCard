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
public class SaveWrittenExamRequestDto {
    @NotNull
    String studentToken;
    int correctAnswers;
}
