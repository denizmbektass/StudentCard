package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveTeamworkRequestDto;
import com.bilgeadam.dto.response.GetTeamworkResponseDto;
import com.bilgeadam.repository.entity.Teamwork;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITeamworkMapper {
    ITeamworkMapper INSTANCE = Mappers.getMapper(ITeamworkMapper.class);

    Teamwork fromSaveTeamworkRequestDtoToTeamwork(final SaveTeamworkRequestDto dto);

    GetTeamworkResponseDto fromTeamworkToGetTeamworkResponseDto(final Teamwork teamwork);
}
