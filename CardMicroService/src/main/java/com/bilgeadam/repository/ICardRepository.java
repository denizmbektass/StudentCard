package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends MongoRepository<Card,String> {

}
