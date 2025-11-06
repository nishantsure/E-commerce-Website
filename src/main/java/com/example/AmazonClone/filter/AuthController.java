package com.example.AmazonClone.filter;

import com.example.AmazonClone.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @Value("${jwt.secret:MySuperSecretKeyMySuperSecretKey123}")
    private String secret;

    @PostMapping("/refresh")
    public Map<String, String> refreshAccessToken(@RequestHeader(ApplicationConstants.REFRESH_HEADER) String refreshToken) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            String username = claims.get("username", String.class);

            // ✅ Generate new access token
            String newAccessToken = Jwts.builder()
                    .issuer("Amazon Clone")
                    .subject("Access Token")
                    .claim("username", username)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .signWith(secretKey)
                    .compact();

            return Map.of(
                    "accessToken", newAccessToken,
                    "message", "New access token generated successfully!"
            );

        } catch (Exception e) {
            return Map.of("error", "Invalid or expired refresh token!");
        }
    }
}
