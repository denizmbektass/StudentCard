package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.repository.entity.GroupStudent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IGroupStudentMapper {
    IGroupStudentMapper INSTANCE = Mappers.getMapper(IGroupStudentMapper.class);

    GroupStudent fromSaveGroupStudentRequestDtoToGroupStudent(final SaveGroupStudentRequestDto dto);

}
