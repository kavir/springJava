package com.authh.springjwt.controller;

import com.authh.springjwt.Utils.ResponseClass.ApiResponse;
import com.authh.springjwt.common.controller.BaseController;
import com.authh.springjwt.dto.CreateBillRequest;
import com.authh.springjwt.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.authh.springjwt.common.constant.FieldConstantValue.BILL;
import static com.authh.springjwt.common.constant.FieldConstantValue.SETTLE_BILL;
import static com.authh.springjwt.common.message.SuccessResponseConstant.FETCHED;
import static com.authh.springjwt.common.message.SuccessResponseConstant.SUCCESS_SAVE;

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

        return ResponseEntity.status(HttpStatus.OK).body(
                successResponse(
                        customMessageSource.get(FETCHED, customMessageSource.get(BILL)),
                        billService.getBillDetails(billId)
                )
        );
    }

    @PostMapping("/settle/{billId}")
    public ResponseEntity<?> settleBill(@PathVariable Long billId,
                                        @RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "mpin") String mpin,
                                        @RequestParam(value = "notes", required = false) String note,
                                        @RequestParam(value = "isUseReward", required = false) Boolean isUseReward) {

        return ResponseEntity.status(HttpStatus.OK).body(
                successResponse(customMessageSource.get(SUCCESS_SAVE, customMessageSource.get(SETTLE_BILL)),
                        billService.settleBill(billId, userId, mpin, note, isUseReward))
        );
        }

}
