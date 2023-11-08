package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateTechnicalInterviewRequestDto;
import com.bilgeadam.dto.request.SaveTechnicalInterviewRequestDto;
import com.bilgeadam.dto.response.GetTechnicalInterviewResponseDto;
import com.bilgeadam.repository.entity.TechnicalInterview;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITechnicalInterviewMapper {
    ITechnicalInterviewMapper INSTANCE = Mappers.getMapper(ITechnicalInterviewMapper.class);
    TechnicalInterview toTechnicalInterview(final CreateTechnicalInterviewRequestDto dto);
    TechnicalInterview fromSaveTechnicalInterviewRequestDtoToTechnicalInterview(final SaveTechnicalInterviewRequestDto dto);
    GetTechnicalInterviewResponseDto fromTechnicalInterviewToGetTechnicalInterviewResponseDto(final TechnicalInterview technicalInterview);
    TechnicalInterview fromCreateTechnicalInterviewRequestDtoToTechnicalInterview(final CreateTechnicalInterviewRequestDto dto);


}
