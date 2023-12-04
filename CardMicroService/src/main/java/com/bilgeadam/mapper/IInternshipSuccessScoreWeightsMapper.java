package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateInternshipSuccessScoreWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateInternshipSuccsessScoreWeightsRequestDto;
import com.bilgeadam.repository.entity.InternshipSuccessScoreWeights;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IInternshipSuccessScoreWeightsMapper {
    IInternshipSuccessScoreWeightsMapper INSTANCE = Mappers.getMapper(IInternshipSuccessScoreWeightsMapper.class);
    InternshipSuccessScoreWeights toSaveInternshipSuccessScoreWeights(CreateInternshipSuccessScoreWeightsRequestDto createInternshipSuccessScoreWeightsRequestDto);
    InternshipSuccessScoreWeights toUpdateInternshipSuccessScoreWeights(UpdateInternshipSuccsessScoreWeightsRequestDto updateInternshipSuccsessScoreWeightsRequestDto);
}
