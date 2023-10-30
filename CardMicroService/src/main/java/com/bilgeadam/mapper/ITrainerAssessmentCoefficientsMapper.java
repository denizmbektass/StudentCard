package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.UpdateTrainerAssessmentCoefficientsRequestDto;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ITrainerAssessmentCoefficientsMapper {

    ITrainerAssessmentCoefficientsMapper INSTANCE = Mappers.getMapper(ITrainerAssessmentCoefficientsMapper.class);
    TrainerAssessmentCoefficients toUpdateTrainerAssessmentCoefficients(final UpdateTrainerAssessmentCoefficientsRequestDto dto);
    TrainerAssessmentCoefficients toDeleteTrainerAssessmentCoefficients(final TrainerAssessmentCoefficients trainerAssessmentCoefficients);
}
