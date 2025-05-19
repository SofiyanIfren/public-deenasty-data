package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.HistoricalFigureActivityController;
import com.data.deenasty.deenasty_data.models.HistoricalFigureActivity;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.HistoricalFigureActivityService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(HistoricalFigureActivityController.class)
@Import(SecurityConfig.class)
class HistoricalFigureActivityControllerTest {

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
    private HistoricalFigureActivityService historicalFigureActivityService;

    @Test
    void shouldReturnAllHistoricalFigureActivities() throws Exception {
        HistoricalFigureActivity hfa1 = new HistoricalFigureActivity(
            UUID.randomUUID(), 
            "Conquest of Alexandria", 
            LocalDate.of(332, 10, 1), 
            LocalDate.of(332, 10, 5)
        );

        HistoricalFigureActivity hfa2 = new HistoricalFigureActivity(
            UUID.randomUUID(), 
            "Coronation", 
            LocalDate.of(800, 12, 25), 
            LocalDate.of(800, 12, 25)
        );

        Mockito.when(historicalFigureActivityService.getAllHistoricalFigureActivities())
            .thenReturn(List.of(hfa1, hfa2));

        mockMvc.perform(get("/historicalfigure-activity/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].description").value("Conquest of Alexandria"))
            .andExpect(jsonPath("$[1].description").value("Coronation"));
    }

    @Test
    void shouldReturnHistoricalFigureActivityById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        HistoricalFigureActivity hfa = new HistoricalFigureActivity(
            id,
            "Conquest of Alexandria", 
            LocalDate.of(332, 10, 1), 
            LocalDate.of(332, 10, 5)
        );

        Mockito.when(historicalFigureActivityService.getHistoricalFigureActivityById(id))
            .thenReturn(Optional.of(hfa));

        mockMvc.perform(get("/historicalfigure-activity/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Conquest of Alexandria"))
            .andExpect(jsonPath("$.startDate").value("0332-10-01"))
            .andExpect(jsonPath("$.endDate").value("0332-10-05"));
    }

    @Test
    void shouldReturn404_WhenHistoricalFigureActivityNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureActivityService.getHistoricalFigureActivityById(id))
            .thenReturn(Optional.empty());

        mockMvc.perform(get("/historicalfigure-activity/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHistoricalFigureActivity() throws Exception {
        HistoricalFigureActivity hfa = new HistoricalFigureActivity(
            UUID.randomUUID(), 
            "Conquest of Alexandria", 
            LocalDate.of(332, 10, 1), 
            LocalDate.of(332, 10, 5)
        );

        Mockito.when(historicalFigureActivityService.createHistoricalFigureActivity(any(HistoricalFigureActivity.class)))
            .thenReturn(hfa);

        mockMvc.perform(post("/historicalfigure-activity").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "Conquest of Alexandria",
                        "startDate": "0332-10-01",
                        "endDate": "0332-10-05"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description").value("Conquest of Alexandria"))
            .andExpect(jsonPath("$.startDate").value("0332-10-01"))
            .andExpect(jsonPath("$.endDate").value("0332-10-05"));
    }

    @Test
    void shouldUpdateHistoricalFigureActivity_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureActivity updated = new HistoricalFigureActivity(
            id,
            "Conquest of Alexandria", 
            LocalDate.of(332, 10, 1), 
            LocalDate.of(332, 10, 5)
        );

        Mockito.when(historicalFigureActivityService.updateHistoricalFigureActivity(eq(id), any(HistoricalFigureActivity.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/historicalfigure-activity/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "Conquest of Alexandria",
                        "startDate": "0332-10-01",
                        "endDate": "0332-10-05"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Conquest of Alexandria"))
            .andExpect(jsonPath("$.startDate").value("0332-10-01"))
            .andExpect(jsonPath("$.endDate").value("0332-10-05"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentHistoricalFigureActivity() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureActivityService.updateHistoricalFigureActivity(eq(id), any(HistoricalFigureActivity.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/historicalfigure-activity/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "Conquest of Alexandria",
                        "startDate": "0332-10-01",
                        "endDate": "0332-10-05"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteHistoricalFigureActivity_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureActivityService.deleteHistoricalFigureActivity(id))
            .thenReturn(true);

        mockMvc.perform(delete("/historicalfigure-activity/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentHistoricalFigureActivity() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureActivityService.deleteHistoricalFigureActivity(id))
            .thenReturn(false);

        mockMvc.perform(delete("/historicalfigure-activity/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
