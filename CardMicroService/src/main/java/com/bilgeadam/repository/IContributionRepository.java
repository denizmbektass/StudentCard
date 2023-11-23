package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Contribution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IContributionRepository extends MongoRepository<Contribution, String> {


  Optional<Contribution> findByStudentId(String studentId);

}
