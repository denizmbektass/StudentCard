package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateMainWeightsRequestDto;
import com.bilgeadam.dto.request.CreateStudentChoiceWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateMainWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateStudentChoiceWeightsRequestDto;
import com.bilgeadam.repository.entity.MainWeights;
import com.bilgeadam.repository.entity.StudentChoiceWeights;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IStudentChoiceWeightsMapper {
    IStudentChoiceWeightsMapper INSTANCE = Mappers.getMapper(IStudentChoiceWeightsMapper.class);

    StudentChoiceWeights toSaveStudentChoiceWeights(final CreateStudentChoiceWeightsRequestDto createStudentChoiceWeightsRequestDto);

    StudentChoiceWeights toUpdateStudentChoiceWeights(final UpdateStudentChoiceWeightsRequestDto choiceWeightsRequestDto);
}
