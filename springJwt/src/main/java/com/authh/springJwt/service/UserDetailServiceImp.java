package com.authh.springJwt.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.authh.springJwt.model.CustomUserDetails;
import com.authh.springJwt.model.User;
import com.authh.springJwt.repo.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
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


}

