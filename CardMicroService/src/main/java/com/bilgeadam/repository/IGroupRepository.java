package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.repository.view.VwGroupStudentResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IGroupRepository extends MongoRepository<Group,String> {
    Boolean existsByGroupNameIgnoreCase(String groupName);

    @Query("select new com.bilgeadam.repository.view.VwGroupResponseDto(g.groupName)" +
            "from Group g")
    List<VwGroupResponseDto> findAllGroupList();
}
