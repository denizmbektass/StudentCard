package com.bilgeadam.mapper;

import com.bilgeadam.dto.response.CardResponseDto;
import com.bilgeadam.repository.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICardMapper {

    ICardMapper INSTANCE = Mappers.getMapper(ICardMapper.class);

    CardResponseDto toCardResponseDto(final Card card);
}
