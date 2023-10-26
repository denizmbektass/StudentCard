package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Algorithm;
import com.mongodb.RequestContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlgorithmRepository extends MongoRepository<Algorithm,String> {

    List<Algorithm> findAllByStudentId(String studentId);

    Optional<Algorithm> findByStudentId(String studentId);
}
