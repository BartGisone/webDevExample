package com.HBauction.webapp.repository;

import com.HBauction.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * JpaRepository for User
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); 
    Optional<User> findByUsernameAndPassword(String username, String password); 
    Optional<User> findById(Long id);
    
}
