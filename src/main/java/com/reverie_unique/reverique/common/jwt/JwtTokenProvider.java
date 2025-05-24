package com.reverie_unique.reverique.common.jwt;

import com.reverie_unique.reverique.domain.user.entity.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long ACCESS_TOKEN_EXPIRY = 1000 * 60 * 30; // 30분
    private final long REFRESH_TOKEN_EXPIRY = 1000L * 60 * 60 * 24 * 7; // 7일

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }

    public long getRefreshTokenExpiry() {
        return REFRESH_TOKEN_EXPIRY;
    }

    public long getAccessTokenExpiry() {
        return ACCESS_TOKEN_EXPIRY;
    }
}
