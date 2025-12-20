package com.authh.springjwt.Authentication.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.authh.springjwt.Authentication.model.CustomUserDetails;
import com.authh.springjwt.Authentication.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

//    private final String SECRETE_KEY="913cfccc96cc2f2a8d96001cdf5082a9831c352d3592e20bec29a4e8fadda593";
    private final String SECRETE_KEY="kTz8zJbMLyqNlgAc31CCqYMcNS01kuIL7Cmk6PrdpZM=";

    public String extractPhoneNumber(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Long extractUserId(String token){
        return Long.parseLong(extractClaim(token,Claims::getSubject));
    }

    public boolean isValid(String token, UserDetails userDetails) {
        Long userId = extractUserId(token);
        return userId.equals(((CustomUserDetails) userDetails).getId())
               && !isTokenExpired(token);
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
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){
        return Jwts.builder()
        .subject(String.valueOf(user.getId()))
        .claim("role",user.getRole())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
        .signWith(getSignInKey()).compact();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes=Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
