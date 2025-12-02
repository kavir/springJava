package com.authh.springJwt.Reward.Controller;

import com.authh.springJwt.Utils.ResponseClass.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Reward.Service.RewardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RewardPointController {

    private final RewardService rewardService;

    @GetMapping("/getRewardPoints/{userId}")
    public ResponseEntity<?> getRewardPoints(@PathVariable(value = "userId") String userId) {
        System.out.println("Fetching reward points for user ID: " + userId);
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Reward Points Fetched successfully", rewardService.fetchRewardPoints(Long.parseLong(userId)));
        return ResponseEntity.ok(apiResponse);
    }
    
    
}
