
package com.authh.springjwt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springjwt.Authentication.model.User;
import com.authh.springjwt.model.RewardModel;

@Repository
public interface RewardRepository extends JpaRepository<RewardModel, Long> {
    Optional<RewardModel> findByUser(User user);
    Optional<RewardModel> findByUserId(Long user_id);

}
