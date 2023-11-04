package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateApplicationProcessRequestDto;
import com.bilgeadam.dto.request.UpdateApplicationProcessRequestDto;
import com.bilgeadam.dto.response.GetApplicationProcessResponseDto;
import com.bilgeadam.repository.entity.ApplicationProcess;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IApplicationProcessMapper {
    IApplicationProcessMapper INSTANCE = Mappers.getMapper(IApplicationProcessMapper.class);

    ApplicationProcess fromCreateApplicationProcessRequestDtoToApplicationProcess(final CreateApplicationProcessRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ApplicationProcess fromUpdateApplicationProcessRequestDtoToApplicationProcess(final UpdateApplicationProcessRequestDto dto, @MappingTarget ApplicationProcess applicationProcess);

    GetApplicationProcessResponseDto fromApplicationProcessToGetApplicationProcessResponseDto(final ApplicationProcess applicationProcess);
}
