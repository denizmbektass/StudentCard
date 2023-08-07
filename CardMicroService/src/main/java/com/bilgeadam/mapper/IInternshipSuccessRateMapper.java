package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.dto.request.UpdateInternshipRequestDto;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IInternshipSuccessRateMapper {
    IInternshipSuccessRateMapper INSTANCE = Mappers.getMapper(IInternshipSuccessRateMapper.class);

    InternshipSuccessRate toInternshipSuccessRateDtoFromInternshipSuccessRate(final InternshipSuccessRateRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InternshipSuccessRate toUpdateInternshipFromInternship(final UpdateInternshipRequestDto dto, @MappingTarget InternshipSuccessRate internship);
}
