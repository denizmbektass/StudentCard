package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.SendStudentsRequestDto;
import com.bilgeadam.dto.request.SaveStudentRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    Student toStudent(final SaveStudentRequestDto dto);

    Iterable<Student> toUsers(final Iterable<SaveStudentRequestDto> dto);

    SaveStudentResponseDto toStudentResponseDto(final Student student);

    FindStudentProfileResponseDto toFindStudentProfileResponseDto(final Student student);

    GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDto(final Student user);

    GroupStudentResponseDto toGroupStudentResponseDto(final Student student);

    List<FindByGroupNameResponseDto> toFindByGroupNameListResponseDto(final List<Student> students);

    Student toStudentFromRegisterRequestDto(final RegisterRequestDto dto);

    GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDtoFromUser(final Student user);

    Student studentToUser(final SendStudentsRequestDto dto);

}
