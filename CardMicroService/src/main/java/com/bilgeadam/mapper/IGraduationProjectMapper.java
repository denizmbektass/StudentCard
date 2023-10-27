package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateGraduationProjectRequestDto;
import com.bilgeadam.dto.response.GetGraduationProjectResponseDto;
import com.bilgeadam.repository.entity.GraduationProject;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IGraduationProjectMapper {
    IGraduationProjectMapper INSTANCE = Mappers.getMapper(IGraduationProjectMapper.class);
    GraduationProject toGraduationProject(final CreateGraduationProjectRequestDto dto);
    GetGraduationProjectResponseDto toGraduationProject(final GraduationProject graduationProject);

}
