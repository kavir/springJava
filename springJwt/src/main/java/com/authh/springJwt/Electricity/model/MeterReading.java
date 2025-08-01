package com.authh.springJwt.Electricity.model;

import java.time.LocalDate;
// import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "electricity_meter_reading")
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="meter_id")
    private Long meterId;
    @Column(name="user_id")
    private Long userId; // Or use @ManyToOne relationship with User
    @Column(name="reading_date")
    private LocalDate readingDate;
    @Column(name="current_reading")
    private Double currentReading;
    @Column(name="previous_reading")
    private Double previousReading;
}
