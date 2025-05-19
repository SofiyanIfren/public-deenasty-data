package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.HistoricalFigureMonumentController;
import com.data.deenasty.deenasty_data.models.HistoricalFigureMonument;
import com.data.deenasty.deenasty_data.services.HistoricalFigureMonumentService;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(HistoricalFigureMonumentController.class)
@Import(SecurityConfig.class)
class HistoricalFigureMonumentControllerTest {

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
    static class TestJacksonConfig {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper;
        }
    }

    @MockitoBean
    private HistoricalFigureMonumentService historicalFigureMonumentService;

    @Test
    void shouldReturnAllHistoricalFigureMonuments() throws Exception {
        HistoricalFigureMonument hfb1 = new HistoricalFigureMonument(UUID.randomUUID(), null, null);
        HistoricalFigureMonument hfb2 = new HistoricalFigureMonument(UUID.randomUUID(), null, null);

        Mockito.when(historicalFigureMonumentService.getAllHistoricalFigureMonuments()).thenReturn(List.of(hfb1, hfb2));

        mockMvc.perform(get("/historicalfigure-monument/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnHistoricalFigureMonumentById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureMonument hfb = new HistoricalFigureMonument(id, null, null);

        Mockito.when(historicalFigureMonumentService.getHistoricalFigureMonumentById(id)).thenReturn(Optional.of(hfb));

        mockMvc.perform(get("/historicalfigure-monument/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturn404_WhenHistoricalFigureMonumentNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureMonumentService.getHistoricalFigureMonumentById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/historicalfigure-monument/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHistoricalFigureMonument() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureMonument hfb = new HistoricalFigureMonument(id, null, null);

        Mockito.when(historicalFigureMonumentService.createHistoricalFigureMonument(any(HistoricalFigureMonument.class))).thenReturn(hfb);

        mockMvc.perform(post("/historicalfigure-monument").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s"
                    }
                """.formatted(id)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldUpdateHistoricalFigureMonument_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureMonument updated = new HistoricalFigureMonument(id, null, null);

        Mockito.when(historicalFigureMonumentService.updateHistoricalFigureMonument(eq(id), any(HistoricalFigureMonument.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/historicalfigure-monument/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s"
                    }
                """.formatted(id)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentHistoricalFigureMonument() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureMonumentService.updateHistoricalFigureMonument(eq(id), any(HistoricalFigureMonument.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/historicalfigure-monument/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteHistoricalFigureMonument_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureMonumentService.deleteHistoricalFigureMonument(id)).thenReturn(true);

        mockMvc.perform(delete("/historicalfigure-monument/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentHistoricalFigureMonument() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureMonumentService.deleteHistoricalFigureMonument(id)).thenReturn(false);

        mockMvc.perform(delete("/historicalfigure-monument/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
