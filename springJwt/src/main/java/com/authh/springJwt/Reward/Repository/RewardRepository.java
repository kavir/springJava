
package com.authh.springJwt.Reward.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Reward.Model.RewardModel;

@Repository
public interface RewardRepository extends JpaRepository<RewardModel, Long> {
    Optional<RewardModel> findByUser(User user);
    Optional<RewardModel> findByUserId(Long user_id);

}
