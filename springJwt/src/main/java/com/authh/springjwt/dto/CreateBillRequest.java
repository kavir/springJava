package com.authh.springjwt.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateBillRequest {
    private String title;
    private Double totalAmount;
    private boolean isEqualSplit;
    private List<ParticipantDTO> participants;
}////