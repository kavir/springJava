package com.authh.springJwt.SplitBill.Controller;

import java.security.Principal;
import java.util.List;

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

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    @Autowired
    private final BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody @Valid CreateBillRequest request,
                                                   @RequestParam Long Id) {
        System.out.println("details while creating is___"+request.getTitle());
        System.out.println("details while creating is___"+request.getTotalAmount());
        return ResponseEntity.ok(billService.createBill(request, Id));
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getMyBills(@RequestParam Long Id) {
        return ResponseEntity.ok(billService.getMyBills(Id));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponse> getBillDetails(@PathVariable Long billId) {
        return ResponseEntity.ok(billService.getBillDetails(billId));
    }

    @PostMapping("/{billId}/settle")
    public ResponseEntity<String> settleBill(@PathVariable Long billId, @RequestParam Long userId,@RequestParam String mpin,
                                                         @RequestParam String note,
                                                         @RequestParam Boolean  isUseReward) {
        return ResponseEntity.ok(billService.settleBill(billId,userId,mpin,note,isUseReward));
    }
}
