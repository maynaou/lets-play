package com.example.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String identifier; // Can be either email or username
    private String password;
}
