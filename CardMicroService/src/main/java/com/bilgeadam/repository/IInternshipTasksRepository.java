package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.InternshipTasks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IInternshipTasksRepository extends MongoRepository<InternshipTasks, String> {
    InternshipTasks findByStudentId(String studentId);
}
