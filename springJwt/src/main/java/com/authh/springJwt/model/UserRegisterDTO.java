package com.authh.springJwt.model;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class UserRegisterDTO {
    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String username;

    @NotBlank
    private String number;

    @NotBlank
    private String password;

    @NotBlank
    private String mpin;

    private Role role;
    
}
