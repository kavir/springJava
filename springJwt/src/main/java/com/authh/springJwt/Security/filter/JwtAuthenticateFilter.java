package com.authh.springJwt.Security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authh.springJwt.Authentication.service.JwtService;
import com.authh.springJwt.Authentication.service.UserDetailServiceImp;

import io.micrometer.common.lang.NonNull;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {
        @PostConstruct
    public void init() {
        System.out.println("JwtAuthenticateFilter initialized!");
    }

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailServiceImp userDetailService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
                String authHeader=request.getHeader("Authorization");
                System.out.println("siuuu___Authorization Header: " + authHeader); 
                if (authHeader == null || !authHeader.startsWith("Bearer ")) { // Note the "!"
                filterChain.doFilter(request, response);
                
                return;
            }
                String token=authHeader.substring(7);
                String number=jwtService.extractPhoneNumber(token);
                System.out.println("siuuu___Token: " + token); //add this lineeee
                System.out.println("siuuu___Number: " + number);
                if(number!=null && SecurityContextHolder.getContext().getAuthentication()==null){

                    UserDetails userDetails=userDetailService.loadUserByUsername(number);
                    System.out.println("siuuu___JWT is valid: " + jwtService.isValid(token, userDetails));
                    System.out.println("Authorities: " + userDetails.getAuthorities());
                    System.out.println("Authentication set in SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
                    if (jwtService.isValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                number, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                        // Debug print the authentication
                        System.out.println("Setting Authentication in SecurityContextHolder: " + authToken);
                    
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("Authentication in SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
                    }
                    
                    
                }
                filterChain.doFilter(request, response);
                 System.out.println("siuuu___sakkyo");
	}
}
//