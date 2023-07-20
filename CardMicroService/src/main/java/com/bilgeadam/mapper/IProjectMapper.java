package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.repository.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProjectMapper {
    IProjectMapper INSTANCE = Mappers.getMapper(IProjectMapper.class);
    Project toProject(final CreateProjectScoreRequestDto dto);
    CreateProjectScoreResponseDto toCreateProjectScoreResponseDto (final Project project);
}
