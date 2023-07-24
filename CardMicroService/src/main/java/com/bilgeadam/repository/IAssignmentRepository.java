package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAssignmentRepository extends MongoRepository<Assignment,String> {

    List<Assignment> findAllByStudentId(String studentId);
}
