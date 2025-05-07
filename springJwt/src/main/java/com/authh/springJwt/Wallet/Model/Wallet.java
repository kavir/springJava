package com.authh.springJwt.Wallet.Model;

import com.authh.springJwt.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "wallets")

public class Wallet {//wallet
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance = 0.0;
    private String mPin;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;  //
}
