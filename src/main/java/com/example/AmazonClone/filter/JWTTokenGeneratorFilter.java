package com.example.AmazonClone.filter;

import com.example.AmazonClone.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.Date;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Environment env = getEnvironment();
            if (null != env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String accessToken  = Jwts.builder().issuer("Amazon Clone").subject("Access Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                        .signWith(secretKey).compact();

                String refreshToken = Jwts.builder()
                        .issuer("Amazon Clone")
                        .subject("Refresh Token")
                        .claim("username", authentication.getName())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
                        .signWith(secretKey)
                        .compact();

                response.setHeader(ApplicationConstants.JWT_HEADER, "Bearer " + accessToken);
                response.setHeader(ApplicationConstants.REFRESH_HEADER, refreshToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }

}