package com.example.backend.security;

import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.entities.RefreshToken;
import com.example.backend.repository.RefreshTokenRepository;   

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repository;

    public RefreshToken createRefreshToken(String role, String userId) {

        System.out.println("ROLE = " + role);
        System.out.println("USER_ID = " + userId);

        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setRole(role);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days

        return repository.save(token);
    }

    public RefreshToken verifyToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }
}