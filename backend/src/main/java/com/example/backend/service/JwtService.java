package com.example.backend.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiration))
                .claim("scope", "ROLE_USER")
                .build();

        // --- CORRECTION ICI ---
        // On définit l'en-tête avec l'algorithme HS256 explicitement
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        // On passe l'en-tête ET les claims (claims) aux paramètres
        return this.jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
