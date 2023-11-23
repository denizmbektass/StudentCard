package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCareerEducationResponseDto {
    private short centralHrContinuityPoint;
    private short centralHrActivityPoint;
    private short boostContinuityPoint;
    private short boostActivityPoint;
    private short resumeDeliveryPoint;
    private short resumeConveniencePoint;
    private short resumeUpToDatePoint;
}
