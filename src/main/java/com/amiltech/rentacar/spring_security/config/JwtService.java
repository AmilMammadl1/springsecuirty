package com.amiltech.rentacar.spring_security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtService {
    private SecretKey secretKey;


    @Value("${spring.security.jwt-secret-key}")
    private String jwtSecretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return String.valueOf(claims.get("username"));
    }

    private String generateToken(Map<String, Object> map, String email) {
        long jwtTokenTime = 360000000;
        return Jwts
                .builder()
                .signWith(secretKey)
                .claims(map)
                .issuedAt(new Date(System.currentTimeMillis()))
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + jwtTokenTime))
                .compact();
    }

    public String generateToken(String email, Map<String, Object> extraClaims) {
        return generateToken(extraClaims, email);
    }

    public String generateRefresh(String email, Map<String, Object> extraClaims) {
        return generateToken(extraClaims, email);
    }
}
