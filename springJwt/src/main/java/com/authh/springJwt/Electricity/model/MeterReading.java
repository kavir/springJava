package com.authh.springJwt.Electricity.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "electricityMeterReading")
public class MeterReading {
    @Id
    @GeneratedValue
    private Long meterId;
    private Long userId; // Or use @ManyToOne relationship with User
    private LocalDate readingDate;
    private Double currentReading;
    private Double previousReading;
}
