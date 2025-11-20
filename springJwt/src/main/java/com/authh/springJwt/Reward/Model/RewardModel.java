package com.authh.springJwt.Reward.Model;

import com.authh.springJwt.Authentication.model.User;
// import com.authh.springJwt.Wallet.Model.Transaction;

// import jakarta.persistence.Column;
import jakarta.persistence.*;
// import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
@Table(name = "reward")
public class RewardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reward_points")
    private double rewardPoints;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
