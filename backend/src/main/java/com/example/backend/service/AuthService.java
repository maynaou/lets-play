package com.example.backend.service;

import org.springframework.stereotype.Service;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.security.JwtService;
import com.example.backend.security.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.backend.entities.RefreshToken;
import com.example.backend.entities.UserAuth;
import com.example.backend.exception.EmailAlreadyExistsException;
import com.example.backend.exception.UsernameAlreadyExistsException;
import com.example.backend.dto.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;
import com.example.backend.dto.LoginResponse;
import org.springframework.security.core.Authentication;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public void register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already in use");
        }

        UserAuth user = UserAuth.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String role = userDetails.getRole();
        String userId = userDetails.getId(); 

        String token = jwtService.generateToken(role, userId);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(role, userId);

        return ResponseEntity.ok(new LoginResponse(token, refreshToken.getToken()));
    }

    public String refresh(String refreshToken) {
        RefreshToken token = refreshTokenService.verifyToken(refreshToken);
        return jwtService.generateToken(token.getRole(), token.getUserId());
    }



}
