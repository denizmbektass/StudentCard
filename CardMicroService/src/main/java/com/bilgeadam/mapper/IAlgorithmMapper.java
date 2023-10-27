package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateAlgorithmRequestDto;
import com.bilgeadam.dto.response.AlgorithmResponseDto;
import com.bilgeadam.repository.entity.Algorithm;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAlgorithmMapper {
    IAlgorithmMapper INSTANCE = Mappers.getMapper(IAlgorithmMapper.class);

    Algorithm toAlgorithm(final CreateAlgorithmRequestDto dto);
    AlgorithmResponseDto fromAlgorithm(final Algorithm algorithm);
}
