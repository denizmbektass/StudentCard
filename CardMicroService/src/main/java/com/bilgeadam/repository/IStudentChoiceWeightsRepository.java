package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.StudentChoiceWeights;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentChoiceWeightsRepository extends MongoRepository<StudentChoiceWeights, String> {
    StudentChoiceWeights findByGroupName(String groupName);
}
