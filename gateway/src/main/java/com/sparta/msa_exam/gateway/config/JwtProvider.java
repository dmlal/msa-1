package com.sparta.msa_exam.gateway.config;

import com.sparta.msa_exam.gateway.common.exception.CustomException;
import com.sparta.msa_exam.gateway.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    private final SecretKey secretKey;

    public JwtProvider(
            @Value("${service.jwt.secret-key}") String secretKey
    ) {
        byte[] key = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(key);
    }


    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseToken(String token) {
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
            return "ADMIN".equals(userRole);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}