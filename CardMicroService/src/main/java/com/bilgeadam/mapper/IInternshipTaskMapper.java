package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveInternshipTaskRequestDto;
import com.bilgeadam.dto.response.GetInternshipTaskResponseDto;
import com.bilgeadam.repository.entity.InternshipTasks;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IInternshipTaskMapper {
    IInternshipTaskMapper INSTANCE = Mappers.getMapper(IInternshipTaskMapper.class);

    InternshipTasks fromSaveInternshipTaskRequestDtoToTeamwork(final SaveInternshipTaskRequestDto dto);

    GetInternshipTaskResponseDto getInternshipTaskResponseDtofromInternshipTasks(final InternshipTasks internshipTasks);
}
