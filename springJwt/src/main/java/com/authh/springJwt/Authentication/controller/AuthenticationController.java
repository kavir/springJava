package com.authh.springJwt.Authentication.controller;

import com.authh.springJwt.Authentication.model.UserLoginDTO;
import com.authh.springJwt.Utils.BaseController;
import com.authh.springJwt.Utils.ResponseClass.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.model.UserRegisterDTO;
import com.authh.springJwt.Authentication.service.AuthenticationResponse;
import com.authh.springJwt.Authentication.service.AuthenticationService;

import jakarta.validation.Valid;

import static com.authh.springJwt.Utils.FieldConstantValue.USER;
import static com.authh.springJwt.Utils.SuccessResponseConstant.*;


@RestController
public class AuthenticationController  {

    @Autowired
    private AuthenticationService authenticationService;

//    @Operation(
//            summary = "Gets a list of districts",
//            description = "This API allows users to get a list of districts based on the specified province ID and optional search keyword. It returns the districts in the preferred language for authorized users"
//    )

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid UserRegisterDTO  request) {
        System.out.println("Registering user: " + request);


        AuthenticationResponse response = authenticationService.registerUser(request);
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "User Registered successfully", response);
        return ResponseEntity.ok(apiResponse);

    }

   
    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody UserLoginDTO request) {
        System.out.println("Logging in user: " + request.getNumber());

        AuthenticationResponse response = authenticationService.authenticate(request);
        ApiResponse<?> apiResponse = new ApiResponse<>(200, "User Logged In successfully", response);
        return ResponseEntity.ok(apiResponse);

    }
    
}
