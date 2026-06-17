package com.example.backend.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "refresh_tokens")
@Data
public class RefreshToken {
    @Id
    private String id;
    @Indexed(unique = true)
    private String token;
    private String userId;
    private String role;
    private Instant expiryDate;
}
