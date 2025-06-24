package com.authh.springJwt.Wallet.Response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private String senderName;
    private String receiverName;
    private Double amount;
    // private Double serviceChargeAmount;
    // private Double discountAmount;
    private LocalDateTime timestamp; // Use LocalDateTime here
    private String status;
    private String transactionRole; // "Sent" or "Received"
    private String phoneNumber; // Add phone number field

    // You can use Lombok to automatically generate the constructor, getters, and setters.
}
