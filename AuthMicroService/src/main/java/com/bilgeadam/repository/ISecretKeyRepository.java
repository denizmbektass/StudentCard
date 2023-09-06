package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.SecretKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISecretKeyRepository extends MongoRepository<SecretKey,String> {
    Optional<SecretKey> findByAuthId(String authId);
}
