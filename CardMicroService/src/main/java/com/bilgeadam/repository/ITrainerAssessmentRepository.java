package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.TrainerAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ITrainerAssessmentRepository extends MongoRepository<TrainerAssessment,String> {

    Optional<TrainerAssessment> findOptionalByTrainerAssessmentId(String id);
    List<TrainerAssessment> findAllByStudentId(String studentId);
}
