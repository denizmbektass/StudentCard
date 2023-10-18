package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreatProjecrBehaviorScoreRequestDto;
import com.bilgeadam.dto.response.CreateProjectBehaviorScoreResponseDto;
import com.bilgeadam.repository.entity.ProjectBehavior;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProjectBehaviorMapper {

    IProjectBehaviorMapper INSTANCE= Mappers.getMapper(IProjectBehaviorMapper.class);

    ProjectBehavior toProjectBehavior(final CreatProjecrBehaviorScoreRequestDto dto);
    CreateProjectBehaviorScoreResponseDto createProjectBehaviorScoreResponseDto (final ProjectBehavior projectBehavior);
}
