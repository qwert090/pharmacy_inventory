package com.example.authservice.service;


import com.example.authservice.config.JwtProperties;
import com.example.authservice.dto.SessionDto;
import com.example.authservice.exception.ExtractClaimsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.application.name}")
    private String applicationName;
    private final JwtProperties jwtProperties;

    public <T> T extractClaim(String token, String claimName, Class<T> claimClass) {
        final Claims claims = extractAllClaims(token);
        log.info("Claims: {}", claims);
        return claims.get(claimName, claimClass);
    }

    public String generateToken(SessionDto session) {
        Map<String, Object> extraClaims = createExtraClaims(session);
        return buildToken(extraClaims, session, jwtProperties.getExpirationMillis());
    }

    public boolean isTokenValid(String token) {
        Date expirationDate = getExpirationDate(token);
        log.info("Is token valid expiration date {}", expirationDate);
        return expirationDate.after(new Date());
    }

    public Date getExpirationDate(String token) {
        return extractClaim(token, Claims.EXPIRATION, Date.class);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            SessionDto session,
            long expiration
    ) {
        log.info("session: {}", session.getUserId());
        log.info("Expiration: {}", expiration);
        return Jwts.builder()
                .claims(extraClaims)
                .subject(session.getEmail())
                .issuer(applicationName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        token = token.trim();
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException exception) {
            throw new ExtractClaimsException("Error to extract jwt claims", exception);
        }
    }

    private Map<String, Object> createExtraClaims(SessionDto session) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("user_id", session.getUserId());
        extraClaims.put("role", session.getRole());
        return extraClaims;
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
