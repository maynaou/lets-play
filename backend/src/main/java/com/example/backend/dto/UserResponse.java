package com.example.backend.dto;

import com.example.backend.entities.UserAuth;

public record UserResponse(
    String id,
    String username,
    String email,
    String role
) {

    public static UserResponse fromUser(UserAuth user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
    
}
