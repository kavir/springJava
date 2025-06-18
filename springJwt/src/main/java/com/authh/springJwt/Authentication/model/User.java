package com.authh.springJwt.Authentication.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.authh.springJwt.Reward.Model.RewardModel;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "authenticusers")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String number;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "mpin", nullable = false)
    @JsonProperty("mpin")
    private String mpin;

    @Column(name = "profile_picture")
    private String profilePicture;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;  
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RewardModel reward;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public boolean isAdmin() {
        return "ADMIN".equals(this.role.name());
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }
}

