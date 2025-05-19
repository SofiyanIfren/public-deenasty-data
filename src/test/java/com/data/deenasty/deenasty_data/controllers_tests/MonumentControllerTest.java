package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.MonumentController;
import com.data.deenasty.deenasty_data.models.Monument;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.MonumentService;
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

@WebMvcTest(MonumentController.class)
@Import(SecurityConfig.class)
class MonumentControllerTest {

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
    private MonumentService monumentService;

    @Test
    void shouldReturnAllMonuments() throws Exception {
        Monument m1 = new Monument(UUID.randomUUID(), LocalDate.now(), LocalDate.now());
        Monument m2 = new Monument(UUID.randomUUID(), LocalDate.now(), LocalDate.now());

        Mockito.when(monumentService.getAllMonuments()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/monument/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnMonumentById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDate constructionDate = LocalDate.of(1500, 1, 1);
        LocalDate destructionDate = LocalDate.of(1600, 1, 1);

        Monument monument = new Monument(id, constructionDate, destructionDate);
        Mockito.when(monumentService.getMonumentById(id)).thenReturn(Optional.of(monument));

        mockMvc.perform(get("/monument/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.constructionDate").value("1500-01-01"))
            .andExpect(jsonPath("$.destructionDate").value("1600-01-01"));
    }

    @Test
    void shouldReturn404_WhenMonumentNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(monumentService.getMonumentById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/monument/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateMonument() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDate constructionDate = LocalDate.of(1000, 1, 1);
        LocalDate destructionDate = LocalDate.of(1100, 1, 1);
        Monument monument = new Monument(id, constructionDate, destructionDate);

        Mockito.when(monumentService.createMonument(any(Monument.class))).thenReturn(monument);

        mockMvc.perform(post("/monument").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "constructionDate": "1000-01-01",
                        "destructionDate": "1100-01-01"
                    }
                """.formatted(id)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.constructionDate").value("1000-01-01"))
            .andExpect(jsonPath("$.destructionDate").value("1100-01-01"));
    }

    @Test
    void shouldUpdateMonument_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDate constructionDate = LocalDate.of(1200, 1, 1);
        LocalDate destructionDate = LocalDate.of(1300, 1, 1);
        Monument updated = new Monument(id, constructionDate, destructionDate);

        Mockito.when(monumentService.updateMonument(eq(id), any(Monument.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/monument/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "constructionDate": "1200-01-01",
                        "destructionDate": "1300-01-01"
                    }
                """.formatted(id)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.constructionDate").value("1200-01-01"))
            .andExpect(jsonPath("$.destructionDate").value("1300-01-01"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentMonument() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(monumentService.updateMonument(eq(id), any(Monument.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/monument/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "constructionDate": "1400-01-01",
                        "destructionDate": "1500-01-01"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteMonument_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(monumentService.deleteMonument(id)).thenReturn(true);

        mockMvc.perform(delete("/monument/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentMonument() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(monumentService.deleteMonument(id)).thenReturn(false);

        mockMvc.perform(delete("/monument/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
