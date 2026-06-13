package com.example.backend.service;

import org.springframework.stereotype.Service;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.entities.RefreshToken;
import com.example.backend.entities.UserAuth;
import com.example.backend.dto.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.backend.security.UserDetailService;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService UserDetailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public String register(RegisterRequest registerRequest) {
        System.out.println("Received registration request: " + registerRequest.getEmail() + ", "
                + registerRequest.getUsername() + ", " + registerRequest.getPassword());

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        ;

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already in use");
        }
        ;
        System.out.println("Registering user: " + registerRequest.getEmail() + ", " + registerRequest.getUsername()
                + ", " + registerRequest.getPassword());
        UserAuth user = UserAuth.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
        return "Registration successful!";
    }

    public void login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword()));
        UserDetails user = UserDetailService.loadUserByUsername(loginRequest.getIdentifier());

        System.out.println("Login successful for user: " + user.getUsername());

        String token = jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(loginRequest.getIdentifier());

        System.out.println("Generated JWT token: " + token);
        System.out.println("Generated refresh token: " + refreshToken.getToken());
    }

    public String refresh(String refreshToken) {
        RefreshToken token = refreshTokenService.verifyToken(refreshToken);
        return jwtService.generateToken(token.getUsername());
    }



}
