package com.hms.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ CSRF not needed for JWT
                .csrf(csrf -> csrf.disable())

                // ❌ No session (JWT = stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ❌ Disable form & basic auth
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🔓 Auth APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // ✅ Swagger allow 🔥
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()

                        // 👤 Patient APIs
                        .requestMatchers("/api/patient/**")
                        .hasRole("PATIENT")

                        // 👑 Admin APIs
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // 🔒 Everything else
                        .anyRequest().authenticated()
                )

                // ✅ JWT Filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}