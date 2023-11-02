package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.CareerEducation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICareerEducationRepository extends MongoRepository<CareerEducation, String> {

    CareerEducation findByStudentId(String studentId);
}
