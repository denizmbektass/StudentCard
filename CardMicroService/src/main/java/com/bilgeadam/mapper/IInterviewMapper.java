package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateInterviewRequestDto;
import com.bilgeadam.dto.request.SaveInterviewRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.Interview;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IInterviewMapper {
    IInterviewMapper INSTANCE = Mappers.getMapper(IInterviewMapper.class);

    InterviewForTranscriptResponseDto toInterviewForTranscriptResponseDto(final Interview interview);

    Interview fromSaveInterviewRequestDtoToInterview(final SaveInterviewRequestDto dto);

    GetCandidateInterviewResponseDto fromInterviewToGetCandidateInterviewResponseDto(final Interview candidateInterview);

}
