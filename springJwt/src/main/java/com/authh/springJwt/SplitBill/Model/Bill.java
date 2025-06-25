package com.authh.springJwt.SplitBill.Model;

import java.time.LocalDateTime;
import java.util.List;

import com.authh.springJwt.Authentication.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @JoinColumn(name="created_by")
    private User createdBy;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy="bill",cascade=CascadeType.ALL )
    private List<BillParticipant> participants;

    
}
