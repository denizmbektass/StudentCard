package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.PersonalMotivation;
import com.bilgeadam.repository.entity.ProjectBehavior;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonalMotivationRepository extends MongoRepository<PersonalMotivation, String> {
    PersonalMotivation findByStudentId (String studentId);
    void deleteByStudentId(String id);
}
