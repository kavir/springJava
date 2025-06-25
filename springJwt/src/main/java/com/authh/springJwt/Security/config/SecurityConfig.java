package com.authh.springJwt.Security.config;

import com.authh.springJwt.Authentication.service.UserDetailServiceImp;
import com.authh.springJwt.Security.filter.JwtAuthenticateFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    private JwtAuthenticateFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("siuuu___SecurityFilterChain initialized!");
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Enable CORS for secured routes
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(req ->
                        req.requestMatchers("/login/**", "/register/**").permitAll()
                                .requestMatchers("/api/employees/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/wallet/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/bills/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/transactions/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/qr/generate**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/ws/transactions**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/getRewardPoints**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                .userDetailsService(userDetailServiceImp)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration config = new CorsConfiguration();

    //     // Use specific origins only if allowCredentials is true
    //     config.setAllowedOrigins(List.of(
    //         "https://emichaa.netlify.app",
    //         "http://localhost:3000" // (optional) add dev frontend too
    //     ));

    //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     config.setAllowedHeaders(List.of("*"));
    //     config.setAllowCredentials(true); // for cookies/auth headers

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);
    //     return source;
    // }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // for dynamic frontend domains
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // if using cookies or Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
// inconsisitent server deploying