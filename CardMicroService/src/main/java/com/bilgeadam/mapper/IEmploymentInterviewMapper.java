package com.bilgeadam.mapper;


import com.bilgeadam.dto.request.CreateEmploymentInterviewRequestDto;
import com.bilgeadam.dto.response.GetEmploymentInterviewResponseDto;
import com.bilgeadam.repository.entity.EmploymentInterview;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IEmploymentInterviewMapper {
    IEmploymentInterviewMapper INSTANCE = Mappers.getMapper(IEmploymentInterviewMapper.class);
    EmploymentInterview toEmploymentInterview(CreateEmploymentInterviewRequestDto dto);
    GetEmploymentInterviewResponseDto toGetEmploymentInterviewResponseDto(EmploymentInterview employmentInterview);
}
