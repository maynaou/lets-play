package com.example.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ✓ "Does the authentication work correctly?"
    @Test
    void register_thenLogin_shouldReturnToken() throws Exception {
        String registerBody = """
            {
                "username": "testuser1",
                "email": "testuser1@example.com",
                "password": "password123"
            }
        """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
            .andExpect(status().isCreated());

        String loginBody = """
            {
                "identifier": "testuser1@example.com",
                "password": "password123"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists()); // ✓ token présent
    }

    // ✓ "Do operations respect the user roles?" — USER ne peut pas accéder à /users
    @Test
    void normalUser_shouldNotAccessAdminEndpoint() throws Exception {
        // register + login un user normal
        registerAndLogin("bob", "bob@example.com");
        String token = loginAndGetToken("bob@example.com");

        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden()); // ✓ 403, pas le bon rôle
    }

    private void registerAndLogin(String username, String email) throws Exception {
        String body = String.format("""
            {"username": "%s", "email": "%s", "password": "password123"}
        """, username, email);

        mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body));
    }

    private String loginAndGetToken(String email) throws Exception {
        String body = String.format("""
            {"identifier": "%s", "password": "password123"}
        """, email);

        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("accessToken").asText();
    }
}