package com.authh.springJwt.Authentication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.authh.springJwt.Authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByNumber(String number);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByNumberAndMpin(String number, String mpin);

}
