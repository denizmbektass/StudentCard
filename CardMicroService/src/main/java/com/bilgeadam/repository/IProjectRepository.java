package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IProjectRepository extends MongoRepository<Project,String> {
    List<Project> findAllByUserId(String userId);
}
