package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.OralExamRequestDto;
import com.bilgeadam.dto.response.OralExamResponseDto;
import com.bilgeadam.repository.entity.OralExam;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOralExamMapper {
    IOralExamMapper INSTANCE = Mappers.getMapper(IOralExamMapper.class);

    OralExam toOralExam(final OralExamRequestDto dto);
    OralExamResponseDto fromOralExam(final OralExam oralExam);
}
