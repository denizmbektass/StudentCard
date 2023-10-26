package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.ChangeTrainerAssessmentCoefficientsRequestDto;
import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITrainerAssessmentMapper {

    ITrainerAssessmentMapper INSTANCE = Mappers.getMapper(ITrainerAssessmentMapper.class);

    TrainerAssessment toTrainerAssessment(final TrainerAssessmentSaveRequestDto dto);
    TrainerAssessmentCoefficients toTrainerAssessmentCoefficients(final ChangeTrainerAssessmentCoefficientsRequestDto dto);
    TrainerAssessment toSaveTrainerAssessmentCoefficients(final TrainerAssessmentCoefficients trainerAssessmentCoefficients);
    DeleteAssessmentResponseDto toDeleteTrainerAssessment(final TrainerAssessment trainerAssessment);
    TrainerAssessmentSaveResponseDto toSaveTrainerAssessment(final TrainerAssessment trainerAssessment);
    UpdateTrainerAssessmentResponseDto toUpdateTrainerAssessment(final TrainerAssessment trainerAssessment);
    TrainerAssessmentForTranscriptResponseDto toTrainerAssessmentForTranscriptResponseDto(final TrainerAssessment trainerAssessment);

}