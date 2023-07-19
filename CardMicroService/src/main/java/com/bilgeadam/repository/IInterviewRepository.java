package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.repository.entity.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInterviewRepository extends MongoRepository<Interview,String> {
}
