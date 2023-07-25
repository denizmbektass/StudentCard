package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExamRepository extends MongoRepository<Exam,String>{
    List<Exam> findAllByStudentId(String studentId);

}
