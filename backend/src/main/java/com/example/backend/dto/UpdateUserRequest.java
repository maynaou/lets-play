package com.example.backend.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    String username;
    String email;
    String role;
}
