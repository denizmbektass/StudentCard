package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupRepository extends MongoRepository<Group,String> {

    Boolean existsByGroupName(String groupName);

    @Query("{'groupNameList': ?0,'roleList': {$in: ?1} }")
    List<User> findUsersByGroupNameListAndInternshipStatus(String groupNameList, List<ERole> eRole);
}
