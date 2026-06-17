package com.example.backend.entities;

import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;
    
}
