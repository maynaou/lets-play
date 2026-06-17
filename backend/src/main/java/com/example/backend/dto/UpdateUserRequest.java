package com.example.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, numbers, and underscore")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be USER or ADMIN")
    private String role;
}
