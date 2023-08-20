package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.ProjectType;
import com.bilgeadam.repository.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProjectTypeRepository extends MongoRepository<ProjectType,String> {
    Optional<ProjectType> findByProjectTypeIgnoreCase(String projectType);
    List<ProjectType> findAllByStatus(EStatus status);
}
