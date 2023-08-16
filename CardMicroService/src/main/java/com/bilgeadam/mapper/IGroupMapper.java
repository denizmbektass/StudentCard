package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.repository.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IGroupMapper {
    IGroupMapper INSTANCE = Mappers.getMapper(IGroupMapper.class);

    Group fromGroupSaveRequestDtoToGroup(final GroupSaveRequestDto dto);

}
