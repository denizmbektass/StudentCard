package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IStudentRepository extends MongoRepository<Student, String> {

    List<Student> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneNumberContaining(String name, String surname, String email, String phoneNumber);

    List<Student> findByGroupNameListIgnoreCase(String groupName);

    @Query("{'groupNameList': { $regex: ?0, $options: 'i' }, 'internShipStatus': { $ne: 'ACTIVE' }, 'roleList': { $in: ?1 } }")
    List<Student> findStudentsByGroupNameListAndInternshipStatus(String groupNameList, List<ERole> eRole);

   // Optional<Student> findByUserId(String userId);

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

}
