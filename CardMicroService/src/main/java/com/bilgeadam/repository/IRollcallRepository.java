package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Rollcall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRollcallRepository extends MongoRepository<Rollcall, String> {
}
