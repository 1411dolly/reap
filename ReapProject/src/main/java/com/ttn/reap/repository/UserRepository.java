package com.ttn.reap.repository;

import com.ttn.reap.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmailAndPassword(String email,String password);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByToken(String token);
    Optional<User> findById(long id);
}
