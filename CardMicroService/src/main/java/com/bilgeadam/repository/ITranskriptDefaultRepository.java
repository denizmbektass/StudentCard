package com.bilgeadam.repository;

import com.bilgeadam.dto.request.TranscriptDefaultRequestDto;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.repository.entity.TranskriptDefault;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITranskriptDefaultRepository extends MongoRepository<TranskriptDefault,String> {
    Boolean existsByMainGroupName(String mainGroupName);
    Optional<TranskriptDefault> findOptionalByMainGroupName(String mainGroupName);

}
