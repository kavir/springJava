package com.authh.springJwt.Authentication.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.authh.springJwt.Authentication.model.CustomUserDetails;
import com.authh.springJwt.Authentication.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRETE_KEY="913cfccc96cc2f2a8d96001cdf5082a9831c352d3592e20bec29a4e8fadda593";

    public String extractPhoneNumber(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            final String phoneNumber = extractPhoneNumber(token);
            return (phoneNumber.equals(((CustomUserDetails) userDetails).getNumber()) && !isTokenExpired(token));
        } else {
            throw new IllegalArgumentException("Expected CustomUserDetails, but got: " + userDetails.getClass().getName());
        }
    }
    

   
        
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }   

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token,Function<Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateToken(User user){
        String token = Jwts.builder()
        .subject(user.getNumber())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
        .signWith(getSignInKey()).compact();


                return token;
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes=Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
}
