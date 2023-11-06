package com.bank.authorization.repository;


import com.bank.authorization.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByProfileIdAndAndPassword(Long profileId,String password);

    Optional<User> findUserByProfileId(String profileId);
}
