package com.authh.springjwt.controller;

import com.authh.springjwt.Authentication.service.UserDetailServiceImp;
import com.authh.springjwt.model.ElectricityBill;
import com.authh.springjwt.model.MeterReading;
import com.authh.springjwt.service.MeterService;
import com.authh.springjwt.Utils.ResponseClass.ApiResponse;
import com.authh.springjwt.service.WalletService;
import com.authh.springjwt.common.controller.BaseController;
import com.authh.springjwt.dto.PayMeterBillRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/meter")
@RequiredArgsConstructor
public class MeterController extends BaseController {

    private final MeterService meterService;
    private final WalletService walletService;
    private final UserDetailServiceImp uerDetailServiceImp;

    @PostMapping("/submit")
    public ResponseEntity<?> submitReading(@RequestParam Long userId,
                                           @RequestParam(value = "currentReading", required = false) Double currentReading) {
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
    public ResponseEntity<?> payBill(@RequestBody PayMeterBillRequestDto payMeterBillRequestDto,
                                     @PathVariable Long billId
//                                     @RequestParam String number,
//                                     @RequestParam String mpin,
//                                     @RequestParam(value = "notes", required = false) String notes,
//                                     @RequestParam(value = "isUseReward", required = false) Boolean isUseReward
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                successResponse(customMessageSource.get("SUCCESS_CREATED",
                                customMessageSource.get("BID")),
                        meterService.payBill(billId, payMeterBillRequestDto)
                )
        );

    }


}
