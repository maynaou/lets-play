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
                .role("USER")
                .build();
        userRepository.save(user);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        // String role = authentication.getAuthorities()
        // .stream()
        // .findFirst()
        // .map(a -> a.getAuthority().replace("ROLE_", ""))
        // .orElse("USER");

        String role = userDetails.getRole();
        String userId = userDetails.getId(); 

        System.out.println(userId + " " + role + " " + userDetails.getUsername());

        String token = jwtService.generateToken(authentication.getName(), role, userId);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(authentication.getName());

        System.out.println("Generated JWT token: " + token);
        System.out.println("Generated refresh token: " + refreshToken.getToken());

        return ResponseEntity.ok(new LoginResponse(token, refreshToken.getToken()));
    }

    public String refresh(String refreshToken) {
        RefreshToken token = refreshTokenService.verifyToken(refreshToken);
        return jwtService.generateToken(token.getUsername(), token.getRole(), token.getId());
    }



}
