package com.hms.appointment_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity   // 🔥 YAHI LAGANA HAI

@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter; // ✅ inject karo

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        // 🔥 Role based access
                        .requestMatchers("/api/appointments/create")
                        .hasRole("PATIENT")

                        .requestMatchers("/api/appointments/doctor/**")
                        .hasRole("DOCTOR")

                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                // ✅ DIRECT bean use (NO method call)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}