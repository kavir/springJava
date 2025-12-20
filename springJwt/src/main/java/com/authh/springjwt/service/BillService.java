package com.authh.springjwt.service;

import java.util.List;

import com.authh.springjwt.dto.CreateBillRequest;
import com.authh.springjwt.dto.BillResponse;

public interface BillService {

    BillResponse createBill(CreateBillRequest request, Long creatorId);

    List<BillResponse> getMyBills(Long creatorId);

    BillResponse getBillDetails(Long billId);

    String settleBill(Long billId, Long userId, String mpin, String note, Boolean isUseReward);
}
