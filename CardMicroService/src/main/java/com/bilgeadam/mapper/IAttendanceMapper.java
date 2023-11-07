package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateAttendanceRequestDto;
import com.bilgeadam.dto.response.GetAttendanceResponseDto;
import com.bilgeadam.repository.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAttendanceMapper {
    IAttendanceMapper INSTANCE = Mappers.getMapper(IAttendanceMapper.class);

    Attendance fromRequestDtoToAttendance(final CreateAttendanceRequestDto dto);

    GetAttendanceResponseDto fromAttendanceToResponseDto(final Attendance attendance);
}
