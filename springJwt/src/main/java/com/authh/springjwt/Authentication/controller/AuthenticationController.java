package com.authh.springjwt.Authentication.controller;

import com.authh.springjwt.Authentication.model.UserLoginDTO;
import com.authh.springjwt.Authentication.model.UserRegisterDTO;
import com.authh.springjwt.Authentication.service.AuthenticationRegisterResponseDto;
import com.authh.springjwt.Authentication.service.AuthenticationResponse;
import com.authh.springjwt.Authentication.service.AuthenticationService;
import com.authh.springjwt.Utils.ResponseClass.ApiResponse;
import com.authh.springjwt.common.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid UserRegisterDTO request) {
        AuthenticationRegisterResponseDto response = authenticationService.registerUser(request);
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "User Registered successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "User Logged In successfully", response);
        return ResponseEntity.ok(apiResponse);

    }

}