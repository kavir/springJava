package com.authh.springJwt.Authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.model.UserRegisterDTO;
import com.authh.springJwt.Authentication.repo.UserRepository;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Repository.WalletRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
     @Autowired
    private WalletRepository walletRepository;
    
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public AuthenticationResponse registerUser(UserRegisterDTO  request) {
        Wallet wallet = new Wallet();
        System.out.println("the mpin from front end is ______"+request.getMpin());
        if (request.getMpin() == null) {
            throw new IllegalArgumentException("MPin must not be null during registration.");
        }
        wallet.setMpin(passwordEncoder.encode(request.getMpin().toString()));
        if (userRepository.findByNumber(request.getNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered.");
        }

        if ("ADMIN".equalsIgnoreCase(request.getRole().name())) {  
            wallet.setBalance(10000000.0);  
        } else {
            wallet.setBalance(10.0);  
        }
        
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setNumber(request.getNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));  
        user.setRole(request.getRole());
        user.setMpin(wallet.getMpin());

        user = userRepository.save(user);
        wallet.setUser(user);  
        walletRepository.save(wallet);
        user.setWallet(wallet);  
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token,"Registration successful");
    }
    
    
    public AuthenticationResponse authenticate(User request){
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNumber(),request.getPassword()));}
        catch (Exception e) {
            throw new BadCredentialsException("Invalid phone number or password");
        }
        User user = userRepository.findByNumber(request.getNumber())
        .orElseThrow(() -> new BadCredentialsException("Phone number or password is incorrect"));
        String token=jwtService.generateToken(user);
        return new AuthenticationResponse(token,"Login successful");

    }
    
}
