package com.authh.springjwt.service;

import com.authh.springjwt.model.ElectricityBill;
import com.authh.springjwt.model.MeterReading;
import com.authh.springjwt.dto.PayMeterBillRequestDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface MeterService {

    MeterReading submitReading(Long userId, Double currentReading);

    List<ElectricityBill> getUserBills(Long userId);

    ElectricityBill getBill(Long billId);

    String payBill(Long billId, PayMeterBillRequestDto payMeterBillRequestDto) throws IOException;
}
