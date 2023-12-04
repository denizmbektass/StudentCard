package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateEducationWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateEducationWeightsRequestDto;
import com.bilgeadam.repository.entity.EducationWeights;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IEducationWeightsMapper {
    IEducationWeightsMapper INSTANCE = Mappers.getMapper(IEducationWeightsMapper.class);
    EducationWeights toSaveEducationWeights(CreateEducationWeightsRequestDto createEducationWeightsRequestDto);
    EducationWeights toUpdateEducationWeights(UpdateEducationWeightsRequestDto updateEducationWeightsRequestDto);
}
