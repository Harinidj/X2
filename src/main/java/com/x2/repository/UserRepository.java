package com.x2.repository;

import com.x2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    // This is used for your current non-Spring Security login implementation
    Optional<User> findByUsernameAndPassword(String username, String password);
}