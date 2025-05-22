
package com.authh.springJwt.Reward.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.authh.springJwt.Reward.Model.RewardModel;
import com.authh.springJwt.model.User;

@Repository
public interface RewardRepository extends JpaRepository<RewardModel, Long> {
    Optional<RewardModel> findByUser(User user);
    Optional<RewardModel> findByUserId(Long user_id);

}
