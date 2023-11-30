package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.MainWeights;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMainWeightsRepository extends MongoRepository<MainWeights, String> {
    MainWeights findByGroupName(String groupName);
}
