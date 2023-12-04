package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.EducationWeights;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationWeightsRepository extends MongoRepository<EducationWeights, String> {
    EducationWeights findByGroupName(String groupName);
}
