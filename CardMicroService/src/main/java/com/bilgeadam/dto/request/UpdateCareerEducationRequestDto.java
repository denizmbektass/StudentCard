package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCareerEducationRequestDto {
    @NotEmpty
    private String studentToken;
    @Min(0)
    @Max(100)
    @NotNull
    private short centralHrContinuityPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short centralHrActivityPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short boostContinuityPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short boostActivityPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short resumeDeliveryPoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short resumeConveniencePoint;
    @Min(0)
    @Max(100)
    @NotNull
    private short resumeUpToDatePoint;
}
