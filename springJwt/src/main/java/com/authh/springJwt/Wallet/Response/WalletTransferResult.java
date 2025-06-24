package com.authh.springJwt.Wallet.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransferResult {
    private String status;
    private double serviceChargeAmount;
    private double discountAmount;
    private double amount;
}
