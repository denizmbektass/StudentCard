package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UserRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    User toUser(final UserRequestDto dto);
    Iterable<User> toUsers(final Iterable<UserRequestDto> dto);
    UserResponseDto toUserResponseDto(final User user);
    FindStudentProfileResponseDto toFindStudentProfileResponseDto(final User user);
    GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDto(final User user);
    GroupStudentResponseDto toGroupStudentResponseDto(final User user);

    List<FindByGroupNameResponseDto> toFindByGroupNameListResponseDto(final List<User> user);
    User toUserFromRegisterRequestDto(final RegisterRequestDto dto);
    GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDtoFromUser(final User user);
}
