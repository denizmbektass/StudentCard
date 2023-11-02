package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.ApplicationProcess;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IApplicationProcessRepository extends MongoRepository<ApplicationProcess, String> {
    Optional<ApplicationProcess> findOptionalByStudentId(String id);

    void deleteApplicationProcessByStudentId(String id);
}
