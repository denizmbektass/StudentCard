package com.bilgeadam.mapper;


import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.RegisterStudentAndTrainerRequestDto;
import com.bilgeadam.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);
// UserResponseDto toUserResponseDto(final User user);
Auth toAuth (final RegisterRequestDto dto,String password);
    Auth toAuth (final RegisterStudentAndTrainerRequestDto dto, String password);
}
