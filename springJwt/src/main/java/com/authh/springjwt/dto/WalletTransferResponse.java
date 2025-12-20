package com.authh.springjwt.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransferResponse {
    private String status;
    private String message;
    private Double amount;
    private Double serviceChargeAmount;

    private String receiverName;
    private String receiverNumber;
}
