package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.repository.entity.CardParameter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICardParameterMapper {

    ICardParameterMapper INSTANCE = Mappers.getMapper(ICardParameterMapper.class);

    CardParameter toCardParameter(final CreateCardParameterRequestDto dto);
}
