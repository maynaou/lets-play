package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    // ✓ "trying to update non-existing user" → doit retourner 404, pas 500
    @Test
    void getNonExistingProduct_shouldReturn404_notServerError() throws Exception {
        mockMvc.perform(get("/api/products/idquinexistepas"))
            .andExpect(status().isNotFound()); // ✓ 404 et pas 500
    }

    // ✓ token manquant → 401, pas 500
    @Test
    void accessProtectedEndpoint_withoutToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isUnauthorized());
    }
}