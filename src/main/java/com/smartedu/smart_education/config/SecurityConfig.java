package com.smartedu.smart_education.config;

import com.smartedu.smart_education.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // ADMIN only
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // ADMIN + TEACHER
                        .requestMatchers("/api/students/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/teachers/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/scores/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/subjects/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/quiz-scores/**").hasAnyRole("ADMIN", "TEACHER")

                        // ADMIN + STUDENT
                        .requestMatchers("/api/insights/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/tests/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/test-responses/**").hasAnyRole("ADMIN", "STUDENT")

                        // ADMIN + PARENT
                        .requestMatchers("/api/parents/**").hasAnyRole("ADMIN", "PARENT")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

