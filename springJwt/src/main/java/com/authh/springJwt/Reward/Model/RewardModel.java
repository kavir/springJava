package com.authh.springJwt.Reward.Model;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Wallet.Model.Transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reward")
public class RewardModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
 
    private double rewardPoints;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    

}
