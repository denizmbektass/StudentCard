package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.GameInterview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGameInterviewRepository extends MongoRepository<GameInterview,String> {
    List<GameInterview> findAllByStudentId(String studentId);
    Optional<GameInterview> findByStudentId(String studenId);
}
