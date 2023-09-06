package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.repository.entity.Absence;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAbsenceMapper {
    IAbsenceMapper INSTANCE = Mappers.getMapper(IAbsenceMapper.class);

    Absence fromAddAbsenceRequestDtoToAbsence(final AddAbsenceRequestDto dto);
}
