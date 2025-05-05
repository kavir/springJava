package com.authh.springJwt.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.authh.springJwt.Wallet.Model.Wallet;

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
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id",unique = true)
    private int id;

    @Column (name = "first_name")
    private String firstname;
    @Column (name = "last_name")
    private String lastname;
    @Column (name = "user_name")
    private String username;
    @Column (name = "phone_number",unique = true)
    private String number;
    @Column (name = "password")
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @Enumerated(value = EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" +role.name()));
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }
    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);  // Check if role is "ADMIN"
    }
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}

    
}
