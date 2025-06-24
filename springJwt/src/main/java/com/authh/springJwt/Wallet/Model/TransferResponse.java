package com.authh.springJwt.Wallet.Model;

import lombok.Data;

@Data
public class TransferResponse {
    private String status;
    private String message;
    private String notes;
    private double amount;
    private double serviceChargeAmount;
    private double discountAmount;
    private String receiverName;
    private String receiverNumber;

    // Constructor
    public TransferResponse(String status, String message,String notes, double amount,double serviceChargeAmount,double discountAmount, String receiverName, String receiverNumber) {
        this.status = status;
        this.message = message;
        this.notes = notes;
        this.amount = amount;
        this.serviceChargeAmount = serviceChargeAmount;
        this.discountAmount = discountAmount;
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
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
    public Double getdiscountAmount() {
        return discountAmount;
    }

    public void setdiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
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
