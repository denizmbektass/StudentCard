package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IAttendanceRepository extends MongoRepository<Attendance,String> {

    Attendance findByStudentId(String studentId);

    void deleteByStudentId(String id);
}
