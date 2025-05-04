package com.authh.springJwt.Electricity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.authh.springJwt.Electricity.model.ElectricityBill;

public interface ElectricityBillRepository extends JpaRepository<ElectricityBill, Long> {
    List<ElectricityBill> findByUserId(Long userId);
}

