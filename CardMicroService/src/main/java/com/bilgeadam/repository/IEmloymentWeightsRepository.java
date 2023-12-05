package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.EmploymentWeights;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmloymentWeightsRepository extends MongoRepository<EmploymentWeights, String> {
    EmploymentWeights findByGroupName(String groupName);
}
