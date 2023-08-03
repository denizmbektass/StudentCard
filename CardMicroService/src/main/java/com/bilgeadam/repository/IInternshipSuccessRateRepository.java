package com.bilgeadam.repository;

import com.bilgeadam.dto.response.InternshipResponseDto;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInternshipSuccessRateRepository extends MongoRepository<InternshipSuccessRate,String> {

    List<InternshipResponseDto> findAllByUserId(String userId);
}
