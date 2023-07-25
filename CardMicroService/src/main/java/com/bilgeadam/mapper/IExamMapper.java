package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.request.CreateExamRequestDto;
import com.bilgeadam.dto.response.ExamResponseDto;
import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IExamMapper {
    IExamMapper INSTANCE = Mappers.getMapper(IExamMapper.class);
    Exam toExam(final CreateExamRequestDto dto);
    ExamResponseDto toExamResponseDto (final Exam exam);
}
