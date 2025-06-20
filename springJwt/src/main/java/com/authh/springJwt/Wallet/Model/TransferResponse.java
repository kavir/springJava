package com.authh.springJwt.Wallet.Model;

import lombok.Data;

@Data
public class TransferResponse {
    private String status;
    private String message;
    private double serviceChargeAmount;
    private double amount;
    private String receiverName;
    private String receiverNumber;

    // Constructor
    public TransferResponse(String status, String message, double amount,double serviceChargeAmount, String receiverName, String receiverNumber) {
        this.status = status;
        this.message = message;
        this.amount = amount;
        this.serviceChargeAmount = serviceChargeAmount;
        this.receiverName = receiverName;
        this.receiverNumber = receiverNumber;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setserviceChargeAmount(Double serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }
}
