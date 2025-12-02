package com.authh.springJwt.Electricity.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.authh.springJwt.Electricity.model.ElectricityBill;
import com.authh.springJwt.Electricity.model.MeterReading;
import com.authh.springJwt.Electricity.repository.ElectricityBillRepository;
import com.authh.springJwt.Electricity.repository.MeterReadingRepository;

@Service
@RequiredArgsConstructor
public class MeterService {

    private final MeterReadingRepository meterReadingRepo;

    private final ElectricityBillRepository billRepo;

    private static final double RATE_PER_UNIT = 16.0;

    public MeterReading submitReading(Long userId, Double currentReading) {
        MeterReading lastReading = meterReadingRepo.findByUserIdOrderByReadingDateDesc(userId)
                .stream().findFirst().orElse(null);

        Double previousReading = (lastReading != null) ? lastReading.getCurrentReading() : 0.0;

        MeterReading newReading = new MeterReading();
        newReading.setUserId(userId);
        newReading.setReadingDate(LocalDate.now());
        newReading.setCurrentReading(currentReading);
        newReading.setPreviousReading(previousReading);

        meterReadingRepo.save(newReading);

        // Generate bill
        double units = currentReading - previousReading;
        double amount = units * RATE_PER_UNIT;

        ElectricityBill bill = new ElectricityBill();
        bill.setUserId(userId);
        bill.setBillDate(LocalDate.now());
        bill.setUnitsConsumed(units);
        bill.setAmount(amount);
        bill.setPaid(false);
        billRepo.save(bill);
        return newReading;
    }

    public List<ElectricityBill> getUserBills(Long userId) {
        return billRepo.findByUserId(userId);
    }
    public ElectricityBill getBill(Long billId) {
        return billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public String payBill(Long billId) {
        ElectricityBill bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (bill.isPaid()) {
            return "Bill already paid.";
        }

        bill.setPaid(true);
        billRepo.save(bill);

        return "Payment successful.";
    }
}
