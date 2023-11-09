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
    @Min(0)
    @Max(100)
    @NotNull
    private short backlogCompletionTime;
    @Min(0)
    @Max(100)
    @NotNull
    private short completedBacklog;
}
