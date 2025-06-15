package com.authh.springJwt.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.authh.springJwt.Wallet.Response.UserUpdateRequest;
import com.authh.springJwt.model.CustomUserDetails;
import com.authh.springJwt.model.Role;
import com.authh.springJwt.model.User;
import com.authh.springJwt.repo.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
        return new CustomUserDetails(user);
    }
    public boolean isValidNumber(String number) {
        return userRepository.findByNumber(number).isPresent();
    }

    public boolean isValidMpin(String number, String rawMpin) {
        Optional<User> userOptional = userRepository.findByNumber(number);
        if (userOptional.isEmpty()) {
            return false;
        }
        String storedHashedMpin = userOptional.get().getMpin();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawMpin, storedHashedMpin);
    }

    public User updateUser(UserUpdateRequest request) {
        System.out.println("Updating user with request: " + request);
        User user = userRepository.findByNumber(request.getNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
        if (request.getFirstName() != null) {
            user.setFirstname(request.getFirstName());
        }
    
        if (request.getLastName() != null) {
            user.setLastname(request.getLastName());
        }
    
        // if (request.getUsername() != null) {
        //     user.setUsername(request.getUsername());
        // }
    
        // if (request.getPassword() != null && !request.getPassword().isBlank()) {
        //     user.setPassword(passwordEncoder.encode(request.getPassword()));
        // }
    
        // if (request.getMpin() != null) {
        //     user.setMpin(request.getMpin());
        // }
    
        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture());
        }
    
        // if (request.getRole() != null) {
        //     try {
        //         user.setRole(Role.valueOf(request.getRole()));
        //     } catch (IllegalArgumentException e) {
        //         throw new RuntimeException("Invalid role: " + request.getRole());
        //     }
        // }
        System.out.println("User after update: " + user);
    
        return userRepository.save(user);
    }
    
    //  public User updateUser(UserUpdateRequest request) {
    //     User user = userRepository.findByNumber(request.getNumber())
    //                     .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    //     if (request.getFirstname() != null) user.setFirstname(request.getFirstname());
    //     if (request.getLastname() != null) user.setLastname(request.getLastname());
    //     if (request.getUsername() != null) user.setUsername(request.getUsername());

    //     if (request.getPassword() != null && !request.getPassword().isEmpty()) {
    //         user.setPassword(passwordEncoder.encode(request.getPassword()));
    //     }

    //     if (request.getMpin() != null) {
    //         user.setMpin(request.getMpin());
    //     }

    //     if (request.getProfilePicture() != null) {
    //         user.setProfilePicture(request.getProfilePicture());
    //     }

    //     if (request.getRole() != null) {
    //         user.setRole(Role.valueOf(request.getRole())); // add error handling if invalid role
    //     }

    //     return userRepository.save(user);
    // }

///
}

