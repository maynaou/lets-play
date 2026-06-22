package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProducts_shouldBeAccessible_withoutAuth() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk()); // ✓ répond à : "Can you access GET Products without auth?"
    }

    @Test
    void getProducts_shouldNeverReturnPassword() throws Exception {
        String response = mockMvc.perform(get("/api/products"))
            .andReturn().getResponse().getContentAsString();

        assertFalse(response.toLowerCase().contains("password"));
    }
}