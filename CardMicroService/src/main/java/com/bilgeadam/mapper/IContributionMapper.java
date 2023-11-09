package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveContributionRequestDto;
import com.bilgeadam.dto.response.GetContributionResponseDto;
import com.bilgeadam.repository.entity.Contribution;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IContributionMapper {
    IContributionMapper INSTANCE = Mappers.getMapper(IContributionMapper.class);

    Contribution fromSaveContributionRequestDtoToContribution(final SaveContributionRequestDto dto);
    GetContributionResponseDto fromContributionToGetContributionResponseDto(final Contribution contribution);
}
