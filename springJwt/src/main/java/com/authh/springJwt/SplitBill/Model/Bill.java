package com.authh.springJwt.SplitBill.Model;

import java.time.LocalDateTime;
import java.util.List;

import com.authh.springJwt.Authentication.model.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="bill")
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name="createdByy")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="bill",cascade=CascadeType.ALL )
    private List<BillParticipant> participants;

    
}
