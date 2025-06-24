package com.authh.springJwt.Authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.model.UserRegisterDTO;
import com.authh.springJwt.Authentication.service.AuthenticationResponse;
import com.authh.springJwt.Authentication.service.AuthenticationService;

import jakarta.validation.Valid;

// import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO  request) {
        System.out.println("Registering user: " + request);
        
            AuthenticationResponse response = authenticationService.registerUser(request);
            return ResponseEntity.ok(response);
       
    }

   
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login( @RequestBody User request) {
        System.out.println("Logging in user: " + request.getNumber());
    // public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequestDTO request) {
        
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    
}
