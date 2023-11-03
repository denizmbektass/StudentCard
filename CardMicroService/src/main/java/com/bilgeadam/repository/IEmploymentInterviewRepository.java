package com.bilgeadam.repository;


import com.bilgeadam.repository.entity.EmploymentInterview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmploymentInterviewRepository extends MongoRepository<EmploymentInterview,String> {

    EmploymentInterview findByStudentId(String studentId);

    void deleteByStudentId(String studentId);
}
