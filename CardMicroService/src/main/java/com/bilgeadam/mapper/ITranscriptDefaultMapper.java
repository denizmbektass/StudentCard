package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.TranscriptDefaultRequestDto;
import com.bilgeadam.dto.response.GetDefaultTranscriptResponseDto;
import com.bilgeadam.dto.response.TranscriptDefaultResponseDto;
import com.bilgeadam.repository.entity.TranskriptDefault;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITranscriptDefaultMapper {
    ITranscriptDefaultMapper INSTANCE = Mappers.getMapper(ITranscriptDefaultMapper.class);
    TranskriptDefault transcriptDefaultRequestDtoToTranskriptDefault(final TranscriptDefaultRequestDto transcriptDefaultRequestDto);
    TranscriptDefaultResponseDto transkriptDefaultToTranscriptDefaultResponseDto(final TranskriptDefault transkriptDefault);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTranscriptDefault(TranscriptDefaultRequestDto transcriptDefaultRequestDto, @MappingTarget TranskriptDefault transkriptDefault);

    GetDefaultTranscriptResponseDto fromTranscriptDefaultToGetDefaultTranscriptResponseDto(final TranskriptDefault transkriptDefault);
}
