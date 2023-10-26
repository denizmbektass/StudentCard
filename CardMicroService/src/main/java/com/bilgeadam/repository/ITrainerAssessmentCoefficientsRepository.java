package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ITrainerAssessmentCoefficientsRepository extends MongoRepository<TrainerAssessmentCoefficients,String> {

    Optional<TrainerAssessmentCoefficients> findOptionalByTrainerAssessmentCoefficientsId(String id);
    List<TrainerAssessmentCoefficients> saveByTrainerAssessmentCoefficientsId(String id);
}
