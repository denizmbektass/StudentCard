package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.InternshipSuccessRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInternshipSuccessRateRepository extends MongoRepository<InternshipSuccessRate,String> {

}
