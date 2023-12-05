package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateEmploymentWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateEmploymentWeightsRequestDto;
import com.bilgeadam.repository.entity.EmploymentWeights;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IEmploymentWeightsMapper {
    IEmploymentWeightsMapper INSTANCE = Mappers.getMapper(IEmploymentWeightsMapper.class);

    EmploymentWeights toSaveEmploymentWeights(final CreateEmploymentWeightsRequestDto createEmploymentWeightsRequestDto);
    EmploymentWeights toUpdateEmploymentWeights(final UpdateEmploymentWeightsRequestDto updateEmploymentWeightsRequestDto);
}
