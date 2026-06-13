package com.example.backend.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAuth {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    
}
