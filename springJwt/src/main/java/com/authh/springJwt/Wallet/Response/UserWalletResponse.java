package com.authh.springJwt.Wallet.Response;

import lombok.Data;

@Data
public class UserWalletResponse {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String userPhoneNumber;
    private String userProfile;
    private Double walletBalance;

    // Constructor, getters, and setters
    public UserWalletResponse(Long userId,String userName, String userPhoneNumber,String userProfile, Double walletBalance,String firstName,String lastName) {
        this.userId=userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfile = userProfile;
        this.walletBalance = walletBalance;
       
    }

    // // Getters and setters...
    // public String getUserName() {
    //     return userName;
    // }
    // public Long getUserId() {
    //     return id;
    // }
    // public String getUserProfile() {
    //     return userProfile;
    // }

    // public void setUserName(String userName) {
    //     this.userName = userName;
    // }
    // public void setUserProfile(String userProfile) {
    //     this.userProfile = userProfile;
    // }

    // public String getUserPhoneNumber() {
    //     return userPhoneNumber;
    // }

    // public void setUserPhoneNumber(String userPhoneNumber) {
    //     this.userPhoneNumber = userPhoneNumber;
    // }
    // public Double getWalletBalance() {
    //     return walletBalance;
    // }

    // public void setWalletBalance(Double walletBalance) {
    //     this.walletBalance = walletBalance;
    // }
}
