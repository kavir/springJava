package com.authh.springJwt.Reward.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authh.springJwt.Reward.Repository.RewardRepository;

@Service
public class RewardService {
    @Autowired
    private RewardRepository rewardRepository;

    public String fetchRewardPoints(Long userId) {
        return rewardRepository.findByUserId(userId)
                .map(reward -> "Reward Points: " + reward.getRewardPoints())
                .orElse("User not found or no reward assigned.");
    }
   
}
    

