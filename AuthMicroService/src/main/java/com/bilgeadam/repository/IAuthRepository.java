package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends MongoRepository<Auth, String> {
    Optional<Auth> findByEmail(String email);

    Optional<Auth> findByEmailAndPassword(String email,String password);
    Optional<Auth> findByUserId(String userId);


}
