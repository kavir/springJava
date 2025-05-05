package com.authh.springJwt.Wallet.Response;

public class UserWalletResponse {
    private Long id;
    private String userName;
    private String userPhoneNumber;
    private Double walletBalance;

    // Constructor, getters, and setters
    public UserWalletResponse(Long id,String userName, String userPhoneNumber, Double walletBalance) {
        this.id=id;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.walletBalance = walletBalance;
       
    }

    // Getters and setters...
    public String getUserName() {
        return userName;
    }
    public Long getUserId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
