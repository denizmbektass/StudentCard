package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.OralExam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOralExamRepository extends MongoRepository<OralExam,String> {

    List<OralExam> findAllByStudentId(String studentId);
}
