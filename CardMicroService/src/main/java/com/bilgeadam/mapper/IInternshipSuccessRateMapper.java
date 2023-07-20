package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IInternshipSuccessRateMapper {
    IInternshipSuccessRateMapper INSTANCE = Mappers.getMapper(IInternshipSuccessRateMapper.class);

    InternshipSuccessRate toInternshipSuccessRateDtoFromInternshipSuccessRate(final InternshipSuccessRateRequestDto dto);
}
