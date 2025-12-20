package com.authh.springjwt.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse() {}

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    
}

