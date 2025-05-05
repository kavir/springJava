// LoginRequestDTO.java
package com.authh.springJwt.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Phone number is required")
    private String number;

    @NotBlank(message = "Password is required")
    private String password;
}
