package com.microservices.apigateway.config;

import com.microservices.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter filter;

    public SecurityConfig(
            JwtAuthenticationFilter filter) {

        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain
    securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf ->
                        csrf.disable())

                .cors(cors -> {
                })

                .authorizeHttpRequests(auth ->
                        auth

                                .requestMatchers(
                                        "/auth/**")
                                .permitAll()

                                .requestMatchers(
                                        org.springframework.http.HttpMethod.OPTIONS,
                                        "/**")
                                .permitAll()

                                .anyRequest()
                                .authenticated()
                )

                .addFilterBefore(
                        filter,
                        org.springframework.security.web.authentication
                                .UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}