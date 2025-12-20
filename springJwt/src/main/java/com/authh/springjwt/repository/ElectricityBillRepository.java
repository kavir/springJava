package com.authh.springjwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springjwt.model.ElectricityBill;

@Repository
public interface ElectricityBillRepository extends JpaRepository<ElectricityBill, Long> {
    List<ElectricityBill> findByUserId(Long userId);
    Optional<ElectricityBill> findById(Long id);
}

