package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.repository.entity.TrainerAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ITrainerAssesmentRepository extends MongoRepository<TrainerAssessment,Long> {
    Optional<Card> findOptionalByCardId(String id);
}
