package com.authh.springJwt.Electricity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.authh.springJwt.Electricity.model.MeterReading;
import com.authh.springJwt.Electricity.service.MeterService;

@RestController
@RequestMapping("/api/meter")
public class MeterController {

    @Autowired
    private MeterService meterService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitReading(@RequestParam Long userId, @RequestParam Double currentReading) {
        MeterReading reading = meterService.submitReading(userId, currentReading);
        return ResponseEntity.ok(reading);
    }

    @GetMapping("/bills/{userId}")
    public ResponseEntity<?> getUserBills(@PathVariable Long userId) {
        return ResponseEntity.ok(meterService.getUserBills(userId));
    }

    @PutMapping("/pay/{billId}")
    public ResponseEntity<?> payBill(@PathVariable Long billId) {
        return ResponseEntity.ok(meterService.payBill(billId));
    }
}
