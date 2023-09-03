package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.MainGroup;
import com.bilgeadam.repository.view.VwGroupName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMainGroupRepository extends MongoRepository<MainGroup,String> {

    Optional<MainGroup> findByMainGroupName(String partialGroupName);
    @Query("select new com.bilgeadam.repository.view.VwGroupName(g.mainGroupId,g.mainGroupName) from MainGroup g")
    List<VwGroupName> findAllGroupNamesAsString();
}
