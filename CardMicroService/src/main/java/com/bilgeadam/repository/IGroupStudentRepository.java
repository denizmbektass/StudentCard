package com.bilgeadam.repository;

import com.bilgeadam.repository.view.VwGroupStudentResponseDto;
import com.bilgeadam.repository.entity.GroupStudent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IGroupStudentRepository extends MongoRepository<GroupStudent, String> {

    @Query("select new com.bilgeadam.repository.view.VwGroupStudentResponseDto(gs.groupStudentId,gs.groupId,gs.name,gs.surname)" +
            "from GroupStudent gs")
    List<VwGroupStudentResponseDto> findAllGroupStudentList();

    Boolean existsByGroupStudentId(String groupStudentId);

    Integer countAllByGroupId(String groupId);

    List<GroupStudent> findAllByGroupId(String groupId);

    Boolean existsByUserId(String userId);





}
