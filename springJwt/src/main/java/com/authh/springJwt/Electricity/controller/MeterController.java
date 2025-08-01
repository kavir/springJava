package com.authh.springJwt.Electricity.controller;

import java.io.IOException;
import java.util.List;

import com.authh.springJwt.Authentication.service.AuthenticationResponse;
import com.authh.springJwt.Utils.ResponseClass.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Authentication.service.UserDetailServiceImp;
import com.authh.springJwt.Electricity.model.ElectricityBill;
import com.authh.springJwt.Electricity.model.MeterReading;
import com.authh.springJwt.Electricity.service.MeterService;
import com.authh.springJwt.Wallet.Response.WalletTransferResult;
import com.authh.springJwt.Wallet.Service.WalletService;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping("/api/meter")
public class MeterController {

    @Autowired
    private MeterService meterService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserDetailServiceImp uerDetailServiceImp;

    @PostMapping("/submit")
    public ResponseEntity<?> submitReading(@RequestParam Long userId,
                                           @RequestParam(value="currentReading",required = false) Double currentReading) {
        MeterReading reading = meterService.submitReading(userId, currentReading);
        return ResponseEntity.ok(reading);
    }

    @GetMapping("/bills/{userId}")
    public ResponseEntity<?> getUserBills(@PathVariable(value = "userId") Long userId) {
        List<ElectricityBill> response = meterService.getUserBills(userId);
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Electricity Bill Fetched successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/pay/{billId}")
    public ResponseEntity<?> payBill(@PathVariable Long billId,
                                     @RequestParam String number,
                                     @RequestParam String mpin,
                                     @RequestParam(value="notes",required = false) String notes,
                                     @RequestParam(value="isUseReward",required = false) Boolean isUseReward) {
        System.out.println("Paying bill with ID: " + billId + ", number: " + number );
        if (billId == null) {
            return ResponseEntity.badRequest().body("Missing required path variable: billId");
        }
        if (number == null || number.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Missing required request parameter: number");
        }
        if (mpin == null || mpin.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Missing required request parameter: mpin");
        }
        try {
            ElectricityBill bill = meterService.getBill(billId);
            if (bill == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
            }
            if (!uerDetailServiceImp.isValidNumber(number)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number.");
            }
    
            if (!uerDetailServiceImp.isValidMpin(number, mpin)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid MPIN.");
            }
            String senderNumber = number;
            String receiverNumber = "9876543211";
            double amount = bill.getAmount();

            WalletTransferResult result = walletService.transferFunds(senderNumber, receiverNumber, amount, mpin,notes, isUseReward);
            String transferStatus = result.getStatus();
            if (!transferStatus.equalsIgnoreCase("SUCCESS")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + transferStatus);
            }
            ApiResponse<?> apiResponse = new ApiResponse<>(200, "Electricity Bill Paid successfully", meterService.payBill(billId));
            return ResponseEntity.ok(apiResponse);


        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment: " + e.getMessage());
        }
    }

    
}
