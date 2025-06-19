package com.authh.springJwt.Wallet.Response;

// package com.authh.springJwt.Wallet.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private String status;
    private String message;
    private Double amount;
    private Double serviceChargeAmount;

    private String receiverName;
    private String receiverNumber;
}
