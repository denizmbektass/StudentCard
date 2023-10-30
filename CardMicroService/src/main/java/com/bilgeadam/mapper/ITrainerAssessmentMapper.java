package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.TrainerAssessment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITrainerAssessmentMapper {

    ITrainerAssessmentMapper INSTANCE = Mappers.getMapper(ITrainerAssessmentMapper.class);

    TrainerAssessment toTrainerAssessment(final SaveTrainerAssessmentRequestDto dto);
    DeleteTrainerAssessmentResponseDto toDeleteTrainerAssessment(final TrainerAssessment trainerAssessment);
    TrainerAssessmentSaveResponseDto toSaveTrainerAssessment(final TrainerAssessment trainerAssessment);
    UpdateTrainerAssessmentResponseDto toUpdateTrainerAssessment(final TrainerAssessment trainerAssessment);
    TrainerAssessmentForTranscriptResponseDto toTrainerAssessmentForTranscriptResponseDto(final TrainerAssessment trainerAssessment);

}