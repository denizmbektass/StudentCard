package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.dto.response.FindAllUnRegisteredGroupListResponseDto;
import com.bilgeadam.dto.response.FindByMainGroupIdResponseDto;
import com.bilgeadam.repository.entity.InternshipGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IInternshipGroupMapper {
    IInternshipGroupMapper INSTANCE = Mappers.getMapper(IInternshipGroupMapper.class);

    InternshipGroup fromGroupSaveRequestDtoToInternshipGroup(final GroupSaveRequestDto dto);
    FindByMainGroupIdResponseDto fromGroupToFindByMainGroupIdResponseDto(final InternshipGroup group);



}
