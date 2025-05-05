package com.authh.springJwt.Electricity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.authh.springJwt.Electricity.model.MeterReading;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {
    List<MeterReading> findByUserIdOrderByReadingDateDesc(Long userId);
}
