package com.authh.springJwt.Wallet.Response;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String number;  // phone number (used to identify user)
    private String password; // optional, update if provided
    private String mpin;
    private String profilePicture; // optional
    private String role; 
    
}
