package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Teamwork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamworkRepository extends MongoRepository<Teamwork, String> {
    Teamwork findByStudentId(String studentId);
}
