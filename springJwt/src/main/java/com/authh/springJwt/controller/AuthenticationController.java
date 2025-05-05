package com.authh.springJwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

// import com.authh.springJwt.AuthDTO.LoginRequestDTO;
// import com.authh.springJwt.AuthDTO.RegisterRequestDTO;
import com.authh.springJwt.model.User;
import com.authh.springJwt.service.AuthenticationResponse;
import com.authh.springJwt.service.AuthenticationService;

// import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody User request) {
    // public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequestDTO request) {
        
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login( @RequestBody User request) {
    // public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequestDTO request) {
        
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    
}
