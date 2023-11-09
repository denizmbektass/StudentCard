package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Contribution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContributionRepository extends MongoRepository<Contribution, String> {
    Contribution findByStudentId(String studentId);

}
