package com.example.backend.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.LoginRequest;
import com.example.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("Received registration data: " + registerRequest.getEmail() + ", " + registerRequest.getUsername() + ", " + registerRequest.getPassword());
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received login data: " + loginRequest.getIdentifier() + ", " + loginRequest.getPassword() + ", " + loginRequest.getIdentifier());
        authService.login(loginRequest);
        return "Login endpoint - to be implemented";
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody Map<String, String> body) {
        return authService.refresh(body.get("refreshToken"));
    }

}
