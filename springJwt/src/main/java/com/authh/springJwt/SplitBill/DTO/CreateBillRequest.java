package com.authh.springJwt.SplitBill.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CreateBillRequest {
    private String title;
    private Double totalAmount;
    private boolean isEqualSplit;
    private List<ParticipantDTO> participants;
}////