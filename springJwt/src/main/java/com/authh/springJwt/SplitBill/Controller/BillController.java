package com.authh.springJwt.SplitBill.Controller;

import com.authh.springJwt.SplitBill.DTO.CreateBillRequest;
import com.authh.springJwt.SplitBill.Service.BillService;
import com.authh.springJwt.Utils.ResponseClass.ApiResponse;
import com.authh.springJwt.common.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/// //okay
@RestController
@RequestMapping("/api/splitBills")
@RequiredArgsConstructor
public class BillController extends BaseController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<?> createBill(@RequestBody @Valid CreateBillRequest request,
                                        @RequestParam(value = "creatorId") Long Id) {
        System.out.println("details while creating is___" + request.getTitle());
        System.out.println("details while creating is___" + request.getTotalAmount());
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Bill Created successfully", billService.createBill(request, Id));
        return ResponseEntity.ok(apiResponse);
    }

//    @GetMapping
//    public ResponseEntity<?> getMyBills(@RequestParam(value="userId")  Long Id,@RequestParam(defaultValue = "0") int page,
//                                        @RequestParam(defaultValue = "20") int size) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(successResponse(customMessageSource.get(FETCHED_LIST,
//                        customMessageSource.get(WORK_INTEREST)),
//                        workInterestService.getAllWorkInterestWithPagination(page, size)));
//    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> getBillDetails(@PathVariable(value = "billId") Long billId) {
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Bill Fetched successfully", billService.getBillDetails(billId));
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/settle/{billId}")
    public ResponseEntity<?> settleBill(@PathVariable Long billId, @RequestParam(value = "userId") Long userId, @RequestParam(value = "mpin") String mpin,
                                        @RequestParam(value = "notes", required = false) String note,
                                        @RequestParam(value = "isUseReward", required = false) Boolean isUseReward) {
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "Bill paid successfully", billService.settleBill(billId, userId, mpin, note, isUseReward));
        return ResponseEntity.ok(apiResponse);
    }
}
