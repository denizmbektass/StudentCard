package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.response.DeleteAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentForTranscriptResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.repository.entity.TrainerAssessment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITrainerAssessmentMapper {

    ITrainerAssessmentMapper INSTANCE = Mappers.getMapper(ITrainerAssessmentMapper.class);

    TrainerAssessment toTrainerAssessment(final TrainerAssessmentSaveRequestDto dto);
    DeleteAssessmentResponseDto toDeleteTrainerAssessment(final TrainerAssessment trainerAssessment);
    TrainerAssessmentSaveResponseDto toSaveTrainerAssessment(final TrainerAssessment trainerAssessment);
    UpdateTrainerAssessmentResponseDto toUpdateTrainerAssessment(final TrainerAssessment trainerAssessment);
    TrainerAssessmentForTranscriptResponseDto toTrainerAssessmentForTranscriptResponseDto(final TrainerAssessment trainerAssessment);

}