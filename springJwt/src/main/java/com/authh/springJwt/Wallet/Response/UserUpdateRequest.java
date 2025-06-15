package com.authh.springJwt.Wallet.Response;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String number ; // required, used to identify user
    private String firstName;
    private String lastName;
    // private String username;
    private String editedNumber;  // phone number (used to identify user)
    // private String password; // optional, update if provided
    // private String mpin;
    private String profilePicture; // optional
    // private String role; 
    
}
