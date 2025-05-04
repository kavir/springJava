package com.authh.springJwt.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.authh.springJwt.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNumber(String number);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    
}
