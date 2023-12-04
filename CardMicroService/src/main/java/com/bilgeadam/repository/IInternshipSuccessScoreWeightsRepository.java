package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.InternshipSuccessScoreWeights;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInternshipSuccessScoreWeightsRepository extends MongoRepository<InternshipSuccessScoreWeights, String> {
    InternshipSuccessScoreWeights findByGroupName(String groupName);
}
