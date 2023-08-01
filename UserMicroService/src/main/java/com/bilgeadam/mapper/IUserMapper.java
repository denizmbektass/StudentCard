package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.UserRequestDto;
import com.bilgeadam.dto.response.FindStudentProfileResponseDto;
import com.bilgeadam.dto.response.GetNameAndSurnameByIdResponseDto;
import com.bilgeadam.dto.response.UserResponseDto;
import com.bilgeadam.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    User toUser(final UserRequestDto dto);
    Iterable<User> toUsers(final Iterable<UserRequestDto> dto);
    UserResponseDto toUserResponseDto(final User user);
    FindStudentProfileResponseDto toFindStudentProfileResponseDto(final User user);
    GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDto(final User user);
}
