package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.DeenastyRegionController;
import com.data.deenasty.deenasty_data.models.DeenastyRegion;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.DeenastyRegionService;
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

@WebMvcTest(DeenastyRegionController.class)
@Import(SecurityConfig.class)
class DeenastyRegionControllerTest {

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
    private DeenastyRegionService deenastyRegionService;

    @Test
    void shouldReturnAllDeenastyRegions() throws Exception {
        DeenastyRegion c1 = new DeenastyRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));
        DeenastyRegion c2 = new DeenastyRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyRegionService.getAllDeenastyRegions()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/deenasty-region/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].startDate").value("2020-05-20"))
            .andExpect(jsonPath("$[1].startDate").value("2020-05-20"));
    }

    @Test
    void shouldReturnDeenastyRegionById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        DeenastyRegion Region = new DeenastyRegion(id, LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyRegionService.getDeenastyRegionById(id)).thenReturn(Optional.of(Region));

        mockMvc.perform(get("/deenasty-region/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.startDate").value("2020-05-20"))
            .andExpect(jsonPath("$.endDate").value("2020-05-21"));
    }

    @Test
    void shouldReturn404_WhenDeenastyRegionNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyRegionService.getDeenastyRegionById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/deenasty-region/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDeenastyRegion() throws Exception {
        DeenastyRegion Region = new DeenastyRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyRegionService.createDeenastyRegion(any(DeenastyRegion.class))).thenReturn(Region);

        mockMvc.perform(post("/deenasty-region").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "startDate": "2020-05-20",
                        "endDate": "2020-05-21"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.startDate").value("2020-05-20"))
            .andExpect(jsonPath("$.endDate").value("2020-05-21"));
    }

    @Test
    void shouldUpdateDeenastyRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        DeenastyRegion updated = new DeenastyRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyRegionService.updateDeenastyRegion(eq(id), any(DeenastyRegion.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/deenasty-region/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "startDate": "2020-05-20",
                        "endDate": "2020-05-21"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.startDate").value("2020-05-20"))
            .andExpect(jsonPath("$.endDate").value("2020-05-21"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentDeenastyRegion() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyRegionService.updateDeenastyRegion(eq(id), any(DeenastyRegion.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/deenasty-region/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "startDate": "2020-05-20",
                        "endDate": "2020-05-21"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteDeenastyRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyRegionService.deleteDeenastyRegion(id)).thenReturn(true);

        mockMvc.perform(delete("/deenasty-region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentDeenastyRegion() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyRegionService.deleteDeenastyRegion(id)).thenReturn(false);

        mockMvc.perform(delete("/deenasty-region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
