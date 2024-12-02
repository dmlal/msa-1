package com.sparta.msa_exam.auth.config;

import com.sparta.msa_exam.auth.common.exception.CustomException;
import com.sparta.msa_exam.auth.common.exception.ErrorCode;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.entity.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;

    private final long expirationTime;

    public JwtProvider(
            @Value("${service.jwt.secret-key}") String secretKey,
            @Value("${service.jwt.access-expiration}") long expirationTime
    ) {
        byte[] key = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(key);
        this.expirationTime = expirationTime;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("userRole", user.getUserRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean isAdmin(String token) {
        try {
            Claims claims = parseToken(token);
            String userRole = claims.get("userRole", String.class);
            return UserRole.ADMIN.name().equals(userRole);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}
