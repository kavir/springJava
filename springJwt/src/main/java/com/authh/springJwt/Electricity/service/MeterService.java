package com.authh.springJwt.Electricity.service;

import com.authh.springJwt.Electricity.model.ElectricityBill;
import com.authh.springJwt.Electricity.model.MeterReading;
import com.authh.springJwt.dto.PayMeterBillRequestDto;
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
