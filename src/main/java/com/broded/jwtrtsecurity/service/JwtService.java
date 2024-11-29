package com.broded.jwtrtsecurity.service;

import com.broded.jwtrtsecurity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.signing.lifeTime}")
    private Duration jwtLifeTime;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claims()
                .empty()
                .subject(user.getGuid().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtLifeTime.toMillis()))
                .and()
                .signWith(getSigningKey())
                .compact();
    }
}
