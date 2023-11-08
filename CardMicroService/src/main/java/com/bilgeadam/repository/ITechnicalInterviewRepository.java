package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.TechnicalInterview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITechnicalInterviewRepository extends MongoRepository<TechnicalInterview,String> {
    List<TechnicalInterview> findAllByStudentId(String studentId);
    Optional<TechnicalInterview> findByStudentId(String studenId);
}
