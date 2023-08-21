package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IGroupRepository extends MongoRepository<Group,String> {
    Boolean existsByGroupNameIgnoreCase(String groupName);

    @Query("select new com.bilgeadam.repository.view.VwGroupResponseDto(g.groupId,g.groupName)" +
            "from Group g")
    List<VwGroupResponseDto> findAllGroupList();

    Optional<Group> findByGroupName(String groupName);





}
