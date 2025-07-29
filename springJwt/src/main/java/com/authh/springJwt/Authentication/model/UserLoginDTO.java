package com.authh.springJwt.Authentication.model;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {




    @NotBlank
    private String number;

    @NotBlank
    private String password;



}
