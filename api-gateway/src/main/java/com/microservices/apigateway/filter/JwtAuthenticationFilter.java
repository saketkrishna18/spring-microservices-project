package com.microservices.apigateway.filter;

import com.microservices.apigateway.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException,
            IOException {

        String path =
                request.getServletPath();

        // Public APIs
        if (path.startsWith("/auth")) {

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        String authHeader =
                request.getHeader(
                        HttpHeaders.AUTHORIZATION);

        if (authHeader == null
                || !authHeader.startsWith(
                "Bearer ")) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    "Missing JWT Token");

            return;
        }

        String token =
                authHeader.substring(7);

        boolean valid =
                jwtService.validateToken(
                        token);

        if (!valid) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    "Invalid JWT Token");

            return;
        }

        String email =
                jwtService.extractEmail(
                        token);

        String role =
                jwtService
                        .extractRole(
                                token
                        );



        String method =
                request.getMethod();

        if (path.startsWith(
                "/products")
        ) {

            if (
                    ("POST".equals(
                            method)
                            ||
                            "DELETE".equals(
                                    method)
                    )

                            &&

                            !"ADMIN".equals(
                                    role)
            ) {

                response.setStatus(
                        HttpServletResponse
                                .SC_FORBIDDEN
                );

                response.getWriter()
                        .write(
                                "Only ADMIN can perform this action"
                        );

                return;
            }
        }

        response.setHeader(
                "loggedInUser",
                email
        );
        HttpServletRequestWrapper
                wrappedRequest =
                new HttpServletRequestWrapper(
                        request
                ) {

                    @Override
                    public String getHeader(
                            String name) {

                        if ("loggedInUser"
                                .equals(name)) {

                            return email;
                        }

                        return super.getHeader(
                                name
                        );
                    }
                };

        UsernamePasswordAuthenticationToken
                authentication =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        java.util.Collections.emptyList()
                );

        org.springframework.security.core.context
                .SecurityContextHolder
                .getContext()
                .setAuthentication(
                        authentication
                );

        request.setAttribute(
                "userEmail",
                email);

        filterChain.doFilter(
                wrappedRequest,
                response);


    }
}