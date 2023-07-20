package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.repository.entity.TrainerAssessment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITrainerAssesmentMapper {

    ITrainerAssesmentMapper INSTANCE = Mappers.getMapper(ITrainerAssesmentMapper.class);

    TrainerAssessment toTrainerAssesment(final TrainerAssessmentSaveRequestDto dto);

    TrainerAssessmentSaveResponseDto toSaveTrainerAssesment(final TrainerAssessment trainerAssessment);

    UpdateTrainerAssessmentResponseDto toUpdateTrainerAssessment(final TrainerAssessment trainerAssessment);
}