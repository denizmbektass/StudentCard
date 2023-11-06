package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.DocumentSubmit;
import com.bilgeadam.repository.entity.WrittenExam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDocumentSubmitRepository extends MongoRepository<DocumentSubmit,String> {
    Optional<DocumentSubmit> findByStudentId(String studentId);
}
