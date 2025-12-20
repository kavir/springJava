package com.authh.springjwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.authh.springjwt.repository.RewardRepository;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;

    public String fetchRewardPoints(Long userId) {
        return rewardRepository.findByUserId(userId)
                .map(reward -> "" + reward.getRewardPoints())
                .orElse("User not found or no reward assigned.");
    }
   
}
    

