package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.TeamLeadAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamLeadAssessmentRepository extends MongoRepository<TeamLeadAssessment,String> {
    TeamLeadAssessment findByStudentId(String studentId);
}
