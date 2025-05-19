package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.HistoricalFigureController;
import com.data.deenasty.deenasty_data.enums.Gender;
import com.data.deenasty.deenasty_data.models.HistoricalFigure;
import com.data.deenasty.deenasty_data.services.HistoricalFigureService;
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

@WebMvcTest(HistoricalFigureController.class)
@Import(SecurityConfig.class)
class HistoricalFigureControllerTest {

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
    private HistoricalFigureService historicalFigureService;

    @Test
    void shouldReturnAllHistoricalFigures() throws Exception {
        HistoricalFigure hfb1 = new HistoricalFigure(Gender.valueOf("M"), "Abdul Baqi Miftah", "scholar", "1952", "");
        HistoricalFigure hfb2 = new HistoricalFigure(Gender.valueOf("M"), "Abdel Hamid ibn Badis", "scholar", "1889", "1940");

        Mockito.when(historicalFigureService.getAllHistoricalFigures()).thenReturn(List.of(hfb1, hfb2));

        mockMvc.perform(get("/historicalfigure/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnHistoricalFigureById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigure hfb = new HistoricalFigure(Gender.valueOf("M"), "Abdel Hamid ibn Badis", "scholar", "1889", "1940");

        Mockito.when(historicalFigureService.getHistoricalFigureById(id)).thenReturn(Optional.of(hfb));

        mockMvc.perform(get("/historicalfigure/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Abdel Hamid ibn Badis"));
    }

    @Test
    void shouldReturn404_WhenHistoricalFigureNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureService.getHistoricalFigureById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/historicalfigure/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHistoricalFigure() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigure hfb = new HistoricalFigure(Gender.valueOf("M"), "Abdel Hamid ibn Badis", "scholar", "1889", "1940");

        Mockito.when(historicalFigureService.createHistoricalFigure(any(HistoricalFigure.class))).thenReturn(hfb);

        mockMvc.perform(post("/historicalfigure").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "gender": "M",
                        "name": "Abdel Hamid ibn Badis",
                        "role": "scholar",
                        "birthDate": "1889",
                        "deathDate": "1940"
                    }
                """.formatted(id)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Abdel Hamid ibn Badis"));
    }

    @Test
    void shouldUpdateHistoricalFigure_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigure updated = new HistoricalFigure(Gender.valueOf("M"), "Abdel Hamid ibn Badis", "scholar", "1889", "1940");

        Mockito.when(historicalFigureService.updateHistoricalFigure(eq(id), any(HistoricalFigure.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/historicalfigure/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "gender": "M",
                        "name": "Abdel Hamid ibn Badis",
                        "role": "scholar",
                        "birthDate": "1889",
                        "deathDate": "1940"
                    }
                """.formatted(id)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Abdel Hamid ibn Badis"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentHistoricalFigure() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureService.updateHistoricalFigure(eq(id), any(HistoricalFigure.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/historicalfigure/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "gender": "M",
                        "name": "Abdel Hamid ibn Badis",
                        "role": "scholar",
                        "birthDate": "1889",
                        "deathDate": "1940"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteHistoricalFigure_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureService.deleteHistoricalFigure(id)).thenReturn(true);

        mockMvc.perform(delete("/historicalfigure/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentHistoricalFigure() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureService.deleteHistoricalFigure(id)).thenReturn(false);

        mockMvc.perform(delete("/historicalfigure/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
