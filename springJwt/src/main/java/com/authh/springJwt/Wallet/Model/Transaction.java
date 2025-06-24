package com.authh.springJwt.Wallet.Model;

import java.time.LocalDateTime;

import com.authh.springJwt.Authentication.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString(exclude = {"sender", "receiver"})  
@Entity
@Data
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private Double amount;
    // private Double serviceChargeAmount;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String status;

    @Column(length = 255)
    private String notes;
    public Transaction() {
    }

    public Transaction(User sender, User receiver, Double amount,String notes, String status) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.notes = notes;
        // this.serviceChargeAmount = serviceChargeAmount; // Default service charge, can be set later
        this.status = status;
    }
}
