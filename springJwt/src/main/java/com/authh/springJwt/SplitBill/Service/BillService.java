package com.authh.springJwt.SplitBill.Service;

import java.util.List;

import com.authh.springJwt.SplitBill.DTO.CreateBillRequest;
import com.authh.springJwt.SplitBill.Response.BillResponse;

public interface BillService {
    BillResponse createBill(CreateBillRequest request, String creatorUsername);
    List<BillResponse> getMyBills(String username);
    BillResponse getBillDetails(Long billId);
    String settleBill(Long billId, String username,String mpin,String note,Boolean isUseReward);
}
