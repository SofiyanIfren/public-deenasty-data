package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.HistoricalFigureObedienceController;
import com.data.deenasty.deenasty_data.models.HistoricalFigureObedience;
import com.data.deenasty.deenasty_data.services.HistoricalFigureObedienceService;
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

@WebMvcTest(HistoricalFigureObedienceController.class)
@Import(SecurityConfig.class)
class HistoricalFigureObedienceControllerTest {

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
    private HistoricalFigureObedienceService historicalFigureObedienceService;

    @Test
    void shouldReturnAllHistoricalFigureObediences() throws Exception {
        HistoricalFigureObedience hfb1 = new HistoricalFigureObedience(UUID.randomUUID(), null, null);
        HistoricalFigureObedience hfb2 = new HistoricalFigureObedience(UUID.randomUUID(), null, null);

        Mockito.when(historicalFigureObedienceService.getAllHistoricalFigureObediences()).thenReturn(List.of(hfb1, hfb2));

        mockMvc.perform(get("/historicalfigure-obedience/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnHistoricalFigureObedienceById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureObedience hfb = new HistoricalFigureObedience(id, null, null);

        Mockito.when(historicalFigureObedienceService.getHistoricalFigureObedienceById(id)).thenReturn(Optional.of(hfb));

        mockMvc.perform(get("/historicalfigure-obedience/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturn404_WhenHistoricalFigureObedienceNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureObedienceService.getHistoricalFigureObedienceById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/historicalfigure-obedience/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHistoricalFigureObedience() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureObedience hfb = new HistoricalFigureObedience(id, null, null);

        Mockito.when(historicalFigureObedienceService.createHistoricalFigureObedience(any(HistoricalFigureObedience.class))).thenReturn(hfb);

        mockMvc.perform(post("/historicalfigure-obedience").with(user("admin").roles("ADMIN"))
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
    void shouldUpdateHistoricalFigureObedience_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureObedience updated = new HistoricalFigureObedience(id, null, null);

        Mockito.when(historicalFigureObedienceService.updateHistoricalFigureObedience(eq(id), any(HistoricalFigureObedience.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/historicalfigure-obedience/" + id).with(user("admin").roles("ADMIN"))
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
    void shouldReturn404_WhenUpdatingNonexistentHistoricalFigureObedience() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureObedienceService.updateHistoricalFigureObedience(eq(id), any(HistoricalFigureObedience.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/historicalfigure-obedience/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteHistoricalFigureObedience_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureObedienceService.deleteHistoricalFigureObedience(id)).thenReturn(true);

        mockMvc.perform(delete("/historicalfigure-obedience/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentHistoricalFigureObedience() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureObedienceService.deleteHistoricalFigureObedience(id)).thenReturn(false);

        mockMvc.perform(delete("/historicalfigure-obedience/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
