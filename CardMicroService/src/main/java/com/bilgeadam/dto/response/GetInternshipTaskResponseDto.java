package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInternshipTaskResponseDto {
    private short backlogCompletionTime;
    private short completedBacklog;
}
