package com.securitypractice.security.SecurityConfig.Jwts;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtsUtils {
    @Value("${jwt.secret}")
    private String secretKey;

    public String getTokenFromHeader(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }

    }

    public String CreateToken(String userName) {

        return Jwts.builder().subject(userName)
                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload()
                .getSubject();
    }

    public boolean validateToken(String tokken) {
        try {
            System.out.println("Enter IN Validation.");
            Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(tokken);
            System.out.println("Validation Complete.");
            return true;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
        }
        return false;
    }
}
