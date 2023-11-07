package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateTeamLeadAssesmentRequestDto;
import com.bilgeadam.repository.entity.TeamLeadAssessment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ITeamLeadAssessmentMapper {
    ITeamLeadAssessmentMapper INSTANCE = Mappers.getMapper(ITeamLeadAssessmentMapper.class);
    TeamLeadAssessment toTeamLeadAssessment(final CreateTeamLeadAssesmentRequestDto dto);
}
