package com.authh.springJwt.Electricity.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ElectricityBill {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId; // Or @ManyToOne User
    private String number; // Or @ManyToOne User
    private LocalDate billDate;
    private Double unitsConsumed;
    private Double amount;
    private boolean isPaid=false;
}
