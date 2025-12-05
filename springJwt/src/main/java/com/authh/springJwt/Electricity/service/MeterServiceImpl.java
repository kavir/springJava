package com.authh.springJwt.Electricity.service;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.authh.springJwt.Authentication.service.UserDetailServiceImp;
import com.authh.springJwt.Utils.ResponseClass.ApiResponse;
import com.authh.springJwt.Wallet.Response.WalletTransferResult;
import com.authh.springJwt.Wallet.Service.WalletService;
import com.authh.springJwt.dto.PayMeterBillRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.authh.springJwt.Electricity.model.ElectricityBill;
import com.authh.springJwt.Electricity.model.MeterReading;
import com.authh.springJwt.Electricity.repository.ElectricityBillRepository;
import com.authh.springJwt.Electricity.repository.MeterReadingRepository;

@Service
@RequiredArgsConstructor
public class MeterServiceImpl implements MeterService {

    private final MeterReadingRepository meterReadingRepo;

    private final ElectricityBillRepository billRepo;

    private final WalletService walletService;

    private final UserDetailServiceImp uerDetailServiceImp;

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

    public String payBill(Long billId, PayMeterBillRequestDto payMeterBillRequestDto) throws IOException {
        ElectricityBill bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (bill.isPaid()) {
            return "Bill already paid.";
        }
        System.out.println("Paying bill with ID: " + billId + ", number: " + payMeterBillRequestDto.getNumber());
        if (billId == null) {
//            return ResponseEntity.badRequest().body("Missing required path variable: billId");
        }
        if (payMeterBillRequestDto.getNumber() == null || payMeterBillRequestDto.getNumber().trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Missing required request parameter: number");
        }
        if (payMeterBillRequestDto.getMpin() == null || payMeterBillRequestDto.getMpin().trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Missing required request parameter: mpin");
        }
        if (bill == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
        }
        if (!uerDetailServiceImp.isValidNumber(payMeterBillRequestDto.getNumber())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number.");
        }

        if (!uerDetailServiceImp.isValidMpin(payMeterBillRequestDto.getNumber(), payMeterBillRequestDto.getMpin())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid MPIN.");
        }
        String senderNumber = payMeterBillRequestDto.getNumber();
        String receiverNumber = "9876543211";
        double amount = bill.getAmount();

        WalletTransferResult result = walletService.transferFunds(senderNumber, receiverNumber, amount, payMeterBillRequestDto.getMpin(), payMeterBillRequestDto.getNotes(), payMeterBillRequestDto.getIsUseReward());
        String transferStatus = result.getStatus();
        if (!transferStatus.equalsIgnoreCase("SUCCESS")) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + transferStatus);
        }


        bill.setPaid(true);
        billRepo.save(bill);

        return "Payment successful.";
    }
}
