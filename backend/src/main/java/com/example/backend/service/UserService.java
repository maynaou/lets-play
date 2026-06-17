package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.UpdateUserRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.entities.UserAuth;
import com.example.backend.exception.UserNotFoundException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromUser)
                .toList();
    }

    public UserResponse getUser(String id) {
        UserAuth user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return UserResponse.fromUser(user);
    }

    public UserResponse updateUser(String id, UpdateUserRequest updateUserRequest) {
        UserAuth user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setRole(updateUserRequest.getRole());
        return UserResponse.fromUser(userRepository.save(user));
    }

    public void deleteUser(String id) {
        UserAuth user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

}
