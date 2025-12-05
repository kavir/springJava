package com.authh.springJwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayMeterBillRequestDto {

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^9\\d{9}$", message = "Invalid phone number format.")
    private String number;

    @NotBlank(message = "MPIN is required.")
    @Size(min = 4, max = 4, message = "MPIN must be exactly 4 digits.")
    @Pattern(regexp = "^[0-9]{4}$", message = "MPIN must contain only digits.")
    private String mpin;

    private String notes;

    @NotNull(message = "isUseReward must be provided (true or false).")
    private Boolean isUseReward;
}
