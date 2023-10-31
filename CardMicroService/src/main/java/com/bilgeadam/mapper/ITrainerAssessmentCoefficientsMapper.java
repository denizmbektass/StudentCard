package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.GetGraduationProjectResponseDto;
import com.bilgeadam.dto.response.GetTrainerAssessmentCoefficientsResponseDto;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITrainerAssessmentCoefficientsMapper {

    ITrainerAssessmentCoefficientsMapper INSTANCE = Mappers.getMapper(ITrainerAssessmentCoefficientsMapper.class);
    TrainerAssessmentCoefficients toCreateTrainerAssessmentCoefficients(final SaveTrainerAssessmentRequestDto dto);
    GetTrainerAssessmentCoefficientsResponseDto toTrainerAssessmentCoefficients (final TrainerAssessmentCoefficients trainerAssessmentCoefficients);
}
