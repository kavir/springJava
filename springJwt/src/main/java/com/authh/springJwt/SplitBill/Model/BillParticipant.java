package com.authh.springJwt.SplitBill.Model;

import java.time.LocalDateTime;

import com.authh.springJwt.Authentication.model.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bill_participants")
@Data
public class BillParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double amountOwed;
    @Column(name="has_paid")
    private Boolean hasPaid = false;

    @Column(name="paid_at")
    private LocalDateTime paidAt;
}
