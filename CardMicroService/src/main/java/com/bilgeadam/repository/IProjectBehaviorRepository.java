package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.ProjectBehavior;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectBehaviorRepository extends MongoRepository<ProjectBehavior, String> {

    List<ProjectBehavior> findAllByProjectBehaviorId (String projectBehavorId);
}
