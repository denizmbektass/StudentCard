package com.bilgeadam.repository;


import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrainerAssessmentCoefficientsRepository extends MongoRepository<TrainerAssessmentCoefficients,String> {
    @Override
    List<TrainerAssessmentCoefficients> findAll();

    TrainerAssessmentCoefficients findByStudentId(String id);

    void deleteByStudentId(String id);
}
