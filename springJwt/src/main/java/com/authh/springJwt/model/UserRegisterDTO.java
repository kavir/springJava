package com.authh.springJwt.model;
import lombok.Data;
@Data
public class UserRegisterDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String number;
    private String password;
    private String mpin;
    private Role role;
    
}
