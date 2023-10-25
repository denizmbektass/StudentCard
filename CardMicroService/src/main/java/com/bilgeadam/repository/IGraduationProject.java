package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.GraduationProject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGraduationProject extends MongoRepository<GraduationProject,String>{
    GraduationProject findByStudentId(String studentId);

    void deleteByStudentId(String id);
}
