package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Exam;
import com.bilgeadam.repository.entity.WrittenExam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IWrittenExamRepository extends MongoRepository<WrittenExam,String> {

    Optional<WrittenExam> findByStudentId(String studentId);

}
