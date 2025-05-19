package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.ObedienceController;
import com.data.deenasty.deenasty_data.models.Obedience;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.ObedienceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@WebMvcTest(ObedienceController.class)
@Import(SecurityConfig.class)
public class ObedienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public CustomUserDetailsService customUserDetailsService() {
            return Mockito.mock(CustomUserDetailsService.class);
        }
    }

    @TestConfiguration
    static class JacksonConfig {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper;
        }
    }

    @MockitoBean
    private ObedienceService obedienceService;

    @Test
    void shouldReturnAllObediences() throws Exception {
        Obedience o1 = new Obedience(UUID.randomUUID(), "Name1", "Desc1", LocalDate.now());
        Obedience o2 = new Obedience(UUID.randomUUID(), "Name2", "Desc2", LocalDate.now());

        Mockito.when(obedienceService.getAllObediences()).thenReturn(List.of(o1, o2));

        mockMvc.perform(get("/obedience/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnObedienceById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Obedience o = new Obedience(id, "Spartan Code", "Strict obedience rules", LocalDate.of(500, 1, 1));
        Mockito.when(obedienceService.getObedienceById(id)).thenReturn(Optional.of(o));

        mockMvc.perform(get("/obedience/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.name").value("Spartan Code"))
            .andExpect(jsonPath("$.description").value("Strict obedience rules"))
            .andExpect(jsonPath("$.creationDate").value("0500-01-01"));
    }

    @Test
    void shouldReturn404_WhenObedienceNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(obedienceService.getObedienceById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/obedience/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateObedience() throws Exception {
        UUID id = UUID.randomUUID();
        Obedience o = new Obedience(id, "Zen Code", "Inner peace rules", LocalDate.of(600, 1, 1));
        Mockito.when(obedienceService.createObedience(any(Obedience.class))).thenReturn(o);

        mockMvc.perform(post("/obedience").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "name": "Zen Code",
                        "description": "Inner peace rules",
                        "creationDate": "0600-01-01"
                    }
                """.formatted(id)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.name").value("Zen Code"))
            .andExpect(jsonPath("$.description").value("Inner peace rules"))
            .andExpect(jsonPath("$.creationDate").value("0600-01-01"));
    }

    @Test
    void shouldUpdateObedience_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Obedience o = new Obedience(id, "Updated Name", "Updated Desc", LocalDate.of(700, 1, 1));
        Mockito.when(obedienceService.updateObedience(eq(id), any(Obedience.class))).thenReturn(Optional.of(o));

        mockMvc.perform(put("/obedience/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "name": "Updated Name",
                        "description": "Updated Desc",
                        "creationDate": "0700-01-01"
                    }
                """.formatted(id)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.description").value("Updated Desc"))
            .andExpect(jsonPath("$.creationDate").value("0700-01-01"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentObedience() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(obedienceService.updateObedience(eq(id), any(Obedience.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/obedience/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "name": "Ghost",
                        "description": "No such rule",
                        "creationDate": "0800-01-01"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteObedience_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(obedienceService.deleteObedience(id)).thenReturn(true);

        mockMvc.perform(delete("/obedience/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentObedience() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(obedienceService.deleteObedience(id)).thenReturn(false);

        mockMvc.perform(delete("/obedience/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
