package com.bilgeadam.mapper;


import com.bilgeadam.dto.request.CreatePersonalMotivationRequestDto;
import com.bilgeadam.dto.response.GetGraduationProjectResponseDto;
import com.bilgeadam.dto.response.GetPersonalMotivationResponseDto;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.repository.entity.PersonalMotivation;
import com.bilgeadam.service.PersonalMotivationService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPersonalMotivationMapper {

    IPersonalMotivationMapper INSTANCE= Mappers.getMapper(IPersonalMotivationMapper.class);
    PersonalMotivation toPersonalMotivation(final CreatePersonalMotivationRequestDto dto);
    GetPersonalMotivationResponseDto toPersonalMotivation(final PersonalMotivation personalMotivation);
}
