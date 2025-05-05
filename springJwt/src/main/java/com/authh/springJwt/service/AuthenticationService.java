package com.authh.springJwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.authh.springJwt.AuthDTO.LoginRequestDTO;
import com.authh.springJwt.AuthDTO.RegisterRequestDTO;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Repository.WalletRepository;
import com.authh.springJwt.model.User;
import com.authh.springJwt.repo.UserRepository;

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
    public AuthenticationResponse registerUser(User  request) {
        // Create a new wallet object
        Wallet wallet = new Wallet();
        
        // Set the balance based on the role
        if ("ADMIN".equalsIgnoreCase(request.getRole().name())) {  // Using name() if role is an enum
            wallet.setBalance(10000000.0);  // Set balance to 10,000,000 for admin
        } else {
            wallet.setBalance(10.0);  // Default balance for regular users
        }
        
        // Create a new user object and set its properties
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setNumber(request.getNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));  // Encrypt the password
        user.setRole(request.getRole());
        
        // Save the user to the database
        user = userRepository.save(user);
        
        // Associate the wallet with the user
        wallet.setUser(user);  
        walletRepository.save(wallet);
        
        // Set the wallet reference for the user
        user.setWallet(wallet);  
        
        // Generate a JWT token for the user
        String token = jwtService.generateToken(user);
        
        // Return the authentication response with the token
        return new AuthenticationResponse(token);
    }
    
    
    public AuthenticationResponse authenticate(User request){
    // public AuthenticationResponse authenticate(LoginRequestDTO request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNumber(),request.getPassword()));
        User user=userRepository.findByNumber(request.getNumber()).orElseThrow();
        String token=jwtService.generateToken(user);
        return new AuthenticationResponse(token);

    }
    
}
