package com.authh.springJwt.Electricity.model;

import java.time.LocalDate;

// import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "electricity_bill")
public class ElectricityBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId; // Or @ManyToOne User
    // @Column (name = "phone_number",unique = true)
    // private String number; // Or @ManyToOne User
    @Column(name="bill_date")
    private LocalDate billDate;
    @Column(name="units_consumed")
    private Double unitsConsumed;
    private Double amount;
    @Column(name="is_paid")
    private boolean isPaid=false;
}
