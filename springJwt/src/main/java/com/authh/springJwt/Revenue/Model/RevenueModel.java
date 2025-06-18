package com.authh.springJwt.Revenue.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RevenueModel {
    @Id
    @GeneratedValue
    private Long revenueId;

    private double revenueAmount;
}
