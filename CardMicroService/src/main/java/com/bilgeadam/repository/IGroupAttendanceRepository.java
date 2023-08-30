package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.GroupAttendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IGroupAttendanceRepository extends MongoRepository<GroupAttendance,String> {
    Optional<GroupAttendance> findByAttendanceDateAndGroupId(Date attendanceDate, String groupId);
    List<GroupAttendance> findByGroupId(String groupId);
}
