package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.TrainerAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrainerAssessmentRepository extends MongoRepository<TrainerAssessment,String> {

    // Optional<TrainerAssessment> findOptionalByTrainerAssessmentId(String id);
    List<TrainerAssessment> findAllByStudentId(String studentId);
}
