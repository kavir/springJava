package com.authh.springJwt.Authentication.service;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.model.UserLoginDTO;
import com.authh.springJwt.Authentication.model.UserRegisterDTO;
import com.authh.springJwt.Authentication.repo.UserRepository;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final WalletRepository walletRepository;
    
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationRegisterResponseDto registerUser(UserRegisterDTO  request) {
        Wallet wallet = new Wallet();
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
        return new AuthenticationRegisterResponseDto("Registration successful");
    }

    public AuthenticationResponse authenticate(UserLoginDTO request){
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
