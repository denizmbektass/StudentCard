package com.bilgeadam.mapper;

import com.bilgeadam.dto.response.GroupStudentAttendanceResponseDto;
import com.bilgeadam.repository.entity.GroupAttendance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IGroupAttendanceMapper {
    IGroupAttendanceMapper INSTANCE = Mappers.getMapper(IGroupAttendanceMapper.class);

    GroupStudentAttendanceResponseDto fromGroupAttendanceToGroupStudentAttendanceResponseDto(final GroupAttendance groupAttendance);
}
