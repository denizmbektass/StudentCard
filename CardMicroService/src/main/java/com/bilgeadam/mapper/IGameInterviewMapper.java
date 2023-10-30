package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateGameInterviewRequestDto;
import com.bilgeadam.dto.request.SaveGameInterviewRequestDto;
import com.bilgeadam.dto.response.GetGameInterviewResponseDto;
import com.bilgeadam.repository.entity.GameInterview;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IGameInterviewMapper {
    IGameInterviewMapper INSTANCE = Mappers.getMapper(IGameInterviewMapper.class);
    GameInterview toGameInterview(final CreateGameInterviewRequestDto dto);
    GameInterview fromSaveGameInterviewRequestDtoToGameInterview(final SaveGameInterviewRequestDto dto);
    GetGameInterviewResponseDto fromGameInterviewToGetGameInterviewResponseDto(final GameInterview gameInterview);
    GameInterview fromCreateGameInterviewRequestDtoToGameInterview(final CreateGameInterviewRequestDto dto);


}
