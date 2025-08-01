package com.authh.springJwt.SplitBill.Controller;

import java.util.List;

import com.authh.springJwt.Utils.ResponseClass.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.SplitBill.DTO.CreateBillRequest;
import com.authh.springJwt.SplitBill.Response.BillResponse;
import com.authh.springJwt.SplitBill.Service.BillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
/////okay
/// 
@RestController
@RequestMapping("/api/splitBills")
@RequiredArgsConstructor
public class BillController {

    @Autowired
    private final BillService billService;

    @PostMapping
    public ResponseEntity<?> createBill(@RequestBody @Valid CreateBillRequest request,
                                                   @RequestParam(value="creatorId") Long Id) {
        System.out.println("details while creating is___"+request.getTitle());
        System.out.println("details while creating is___"+request.getTotalAmount());
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Electricity Bill Fetched successfully", billService.createBill(request, Id));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getMyBills(@RequestParam(value="userId")  Long Id) {
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Electricity Bill Fetched successfully", billService.getMyBills(Id));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> getBillDetails(@PathVariable(value="billId")  Long billId) {
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Electricity Bill Fetched successfully", billService.getBillDetails(billId));
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{billId}/settle")
    public ResponseEntity<?> settleBill(@PathVariable Long billId, @RequestParam(value="userId") Long userId,@RequestParam(value="mpin") String mpin,
                                                         @RequestParam(value="notes",required = false) String note,
                                                         @RequestParam(value="isUseReward",required = false) Boolean  isUseReward) {
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Electricity Bill Fetched successfully", billService.settleBill(billId,userId,mpin,note,isUseReward));
        return ResponseEntity.ok(apiResponse);
    }
}
