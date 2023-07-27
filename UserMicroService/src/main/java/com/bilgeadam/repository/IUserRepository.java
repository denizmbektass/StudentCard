package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface IUserRepository extends MongoRepository<User,String> {
    List<User> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneNumberContaining(String name, String surname , String email, String phoneNumber);
}
