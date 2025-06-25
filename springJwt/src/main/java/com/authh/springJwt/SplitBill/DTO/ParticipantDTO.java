package com.authh.springJwt.SplitBill.DTO;

import lombok.Data;

@Data
public class ParticipantDTO {
    private Long userId;
    private Double amountOwed;
}