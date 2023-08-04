package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.request.UpdateProjectRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.dto.response.UpdateProjectResponseDto;
import com.bilgeadam.repository.entity.Project;
import com.mongodb.internal.bulk.UpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProjectMapper {
    IProjectMapper INSTANCE = Mappers.getMapper(IProjectMapper.class);
    Project toProject(final CreateProjectScoreRequestDto dto);
    CreateProjectScoreResponseDto toCreateProjectScoreResponseDto (final Project project);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project updateProjectRequestDtoTOProject(UpdateProjectRequestDto dto, @MappingTarget Project project);
    UpdateProjectResponseDto toUpdateProjectResponseDto ( Project project);

}
