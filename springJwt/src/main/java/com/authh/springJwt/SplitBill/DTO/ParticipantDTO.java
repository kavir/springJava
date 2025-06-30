package com.authh.springJwt.SplitBill.DTO;

import lombok.Data;

@Data
public class ParticipantDTO {
    private String phoneNumber;
    private Double amountOwed;
}