package com.data.deenasty.deenasty_data.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("unused")
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(final CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/**").permitAll() // GET sans auth
            .anyRequest().authenticated() // autres requêtes avec auth
        )
        .httpBasic(Customizer.withDefaults());
    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
