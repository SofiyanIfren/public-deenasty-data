package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.HistoricalFigureRegionController;
import com.data.deenasty.deenasty_data.models.HistoricalFigureRegion;
import com.data.deenasty.deenasty_data.services.HistoricalFigureRegionService;
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

@WebMvcTest(HistoricalFigureRegionController.class)
@Import(SecurityConfig.class)
class HistoricalFigureRegionControllerTest {

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
    private HistoricalFigureRegionService historicalFigureRegionService;

    @Test
    void shouldReturnAllHistoricalFigureRegions() throws Exception {
        HistoricalFigureRegion hfb1 = new HistoricalFigureRegion(UUID.randomUUID(), null, null);
        HistoricalFigureRegion hfb2 = new HistoricalFigureRegion(UUID.randomUUID(), null, null);

        Mockito.when(historicalFigureRegionService.getAllHistoricalFigureRegions()).thenReturn(List.of(hfb1, hfb2));

        mockMvc.perform(get("/historicalfigure-region/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnHistoricalFigureRegionById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureRegion hfb = new HistoricalFigureRegion(id, null, null);

        Mockito.when(historicalFigureRegionService.getHistoricalFigureRegionById(id)).thenReturn(Optional.of(hfb));

        mockMvc.perform(get("/historicalfigure-region/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturn404_WhenHistoricalFigureRegionNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureRegionService.getHistoricalFigureRegionById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/historicalfigure-region/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHistoricalFigureRegion() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureRegion hfb = new HistoricalFigureRegion(id, null, null);

        Mockito.when(historicalFigureRegionService.createHistoricalFigureRegion(any(HistoricalFigureRegion.class))).thenReturn(hfb);

        mockMvc.perform(post("/historicalfigure-region").with(user("admin").roles("ADMIN"))
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
    void shouldUpdateHistoricalFigureRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureRegion updated = new HistoricalFigureRegion(id, null, null);

        Mockito.when(historicalFigureRegionService.updateHistoricalFigureRegion(eq(id), any(HistoricalFigureRegion.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/historicalfigure-region/" + id).with(user("admin").roles("ADMIN"))
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
    void shouldReturn404_WhenUpdatingNonexistentHistoricalFigureRegion() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureRegionService.updateHistoricalFigureRegion(eq(id), any(HistoricalFigureRegion.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/historicalfigure-region/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteHistoricalFigureRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureRegionService.deleteHistoricalFigureRegion(id)).thenReturn(true);

        mockMvc.perform(delete("/historicalfigure-region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentHistoricalFigureRegion() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureRegionService.deleteHistoricalFigureRegion(id)).thenReturn(false);

        mockMvc.perform(delete("/historicalfigure-region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
