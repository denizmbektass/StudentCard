package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.repository.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAssignmentMapper {
    IAssignmentMapper INSTANCE = Mappers.getMapper(IAssignmentMapper.class);

    Assignment toAssignment(final AssignmentRequestDto dto);
    AssignmentResponseDto fromAssignment(final Assignment assignment);
}
