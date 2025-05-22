package com.authh.springJwt.Reward.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Reward.Service.RewardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class RewardPointController {

    @Autowired
    private RewardService rewardService;

    @GetMapping("/getRewardPoints/{userId}")
    public String getRewardPoints(@PathVariable String userId) {
        return rewardService.fetchRewardPoints(Long.parseLong(userId));
    }
    
    
}
