package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateMainWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateMainWeightsRequestDto;
import com.bilgeadam.repository.entity.MainWeights;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMainWeightsMapper {
    IMainWeightsMapper INSTANCE = Mappers.getMapper(IMainWeightsMapper.class);

    MainWeights toSaveMainWeights(final CreateMainWeightsRequestDto mainWeightsRequestDto);
    MainWeights toUpdateMainWeights(final UpdateMainWeightsRequestDto mainWeightsRequestDto);
}
