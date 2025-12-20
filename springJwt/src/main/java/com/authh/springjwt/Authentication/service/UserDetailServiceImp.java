package com.authh.springjwt.Authentication.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authh.springjwt.Authentication.model.CustomUserDetails;
import com.authh.springjwt.Authentication.model.User;
import com.authh.springjwt.Authentication.repo.UserRepository;
import com.authh.springjwt.dto.UserUpdateRequest;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;m
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
        System.out.println("Updating user with request: " + request.getNumber());
        User user = userRepository.findByNumber(request.getNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
        if (request.getFirstName() != null) {
            user.setFirstname(request.getFirstName());
        }
    
        if (request.getLastName() != null) {
            user.setLastname(request.getLastName());
        }
    
    
        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture());
        }
    
        
        System.out.println("User after update: First Name = " + user.getFirstname() +
        ", Last Name = " + user.getLastname() +
        ", Profile Picture = " + user.getProfilePicture());

    
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

