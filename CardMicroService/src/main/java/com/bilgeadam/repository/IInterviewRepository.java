package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.repository.entity.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInterviewRepository extends MongoRepository<Interview,String> {
    List<Interview> findAllByStudentId(String studentId);

    Interview findByStudentId(String studentId);
}
