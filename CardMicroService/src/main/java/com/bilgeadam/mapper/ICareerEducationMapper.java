package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveCareerEducationRequestDto;
import com.bilgeadam.dto.response.GetCareerEducationResponseDto;
import com.bilgeadam.repository.entity.CareerEducation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICareerEducationMapper {
    ICareerEducationMapper INSTANCE = Mappers.getMapper(ICareerEducationMapper.class);

    CareerEducation fromSaveCareerEducationRequestDtoToCareerEducation(final SaveCareerEducationRequestDto dto);

    GetCareerEducationResponseDto fromCareerEducationToGetCareerEducationResponseDto(final CareerEducation careerEducation);
}
