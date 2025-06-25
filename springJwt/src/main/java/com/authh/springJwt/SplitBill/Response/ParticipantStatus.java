package com.authh.springJwt.SplitBill.Response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ParticipantStatus {
    private Long userId;
    private String name;
    private Double amountOwed;
    private Boolean hasPaid;
    private LocalDateTime paidAt;
}
