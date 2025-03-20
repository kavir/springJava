package com.authh.springJwt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final User user; // Assuming you have a User object
    
    public CustomUserDetails(User user) {
        this.user = user;
    }
    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    if (user.isAdmin()) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    } else {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

    


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNumber(); // Return phone number here
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // Custom getter for phone number
    public String getNumber() {
        return user.getNumber();
    }
}
