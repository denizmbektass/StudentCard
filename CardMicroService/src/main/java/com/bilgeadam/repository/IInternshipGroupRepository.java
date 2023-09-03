package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.InternshipGroup;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IInternshipGroupRepository extends MongoRepository<InternshipGroup,String> {
    Boolean existsByGroupNameIgnoreCase(String groupName);

    @Query("select new com.bilgeadam.repository.view.VwGroupResponseDto(g.internShipGroupId,g.groupName)" +
            "from Group g")
    List<VwGroupResponseDto> findAllGroupList();

    Optional<InternshipGroup> findByGroupName(String groupName);

    List<InternshipGroup> findByMainGroupId(String mainGroupId);





}
