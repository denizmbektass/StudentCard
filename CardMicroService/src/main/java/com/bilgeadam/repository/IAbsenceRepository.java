package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Absence;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IAbsenceRepository extends MongoRepository<Absence, String> {
    List<Absence> findByUserId(String userId);
}
