package com.authh.springJwt.Electricity.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Electricity.model.ElectricityBill;
import com.authh.springJwt.Electricity.model.MeterReading;
import com.authh.springJwt.Electricity.service.MeterService;
import com.authh.springJwt.Wallet.Service.WalletService;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping("/api/meter")
public class MeterController {

    @Autowired
    private MeterService meterService;
    @Autowired
    private WalletService walletService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitReading(@RequestParam Long userId, @RequestParam Double currentReading) {
        MeterReading reading = meterService.submitReading(userId, currentReading);
        return ResponseEntity.ok(reading);
    }

    @GetMapping("/bills/{userId}")
    public ResponseEntity<?> getUserBills(@PathVariable Long userId) {
        return ResponseEntity.ok(meterService.getUserBills(userId));
    }
    @PostMapping("/pay/{billId}")
    public ResponseEntity<?> payBill(@PathVariable Long billId,@RequestParam String number,@RequestParam String mPin) {
        try {
            ElectricityBill bill = meterService.getBill(billId);
            if (bill == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
            }

            String senderNumber = number;
            String receiverNumber = "9876543211";
            double amount = bill.getAmount();

            String transferStatus = walletService.transferFunds(senderNumber, receiverNumber, amount,mPin);
            if (!transferStatus.equalsIgnoreCase("SUCCESS")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + transferStatus);
            }

            return ResponseEntity.ok(meterService.payBill(billId));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment: " + e.getMessage());
        }
    }

    // @PutMapping("/pay/{billId}")
    // public ResponseEntity<?> payBill(@PathVariable Long billId) {
    //     return ResponseEntity.ok(meterService.payBill(billId));
    // }
}
