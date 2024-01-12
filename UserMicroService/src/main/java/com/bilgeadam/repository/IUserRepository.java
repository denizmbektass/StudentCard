package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends MongoRepository<User,String> {
    List<User> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneNumberContaining(String name, String surname , String email, String phoneNumber);
    List<User> findByGroupNameListIgnoreCase(String groupName);

    @Query("{'groupNameList': { $regex: ?0, $options: 'i' }, 'internShipStatus': { $ne: 'ACTIVE' }, 'roleList': { $in: ?1 } }")
    List<User> findUsersByGroupNameListAndInternshipStatus(String groupNameList, List<ERole> eRole);

    Optional<User> findByUserId(String userId);

  Optional<User> findByEmail(String email);
}
