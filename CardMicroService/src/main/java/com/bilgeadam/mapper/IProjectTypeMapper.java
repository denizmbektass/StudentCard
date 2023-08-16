package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateProjectTypeRequestDto;
import com.bilgeadam.repository.entity.ProjectType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProjectTypeMapper {
    IProjectTypeMapper INSTANCE = Mappers.getMapper(IProjectTypeMapper.class);
    ProjectType toProjectType(final CreateProjectTypeRequestDto dto);
}
