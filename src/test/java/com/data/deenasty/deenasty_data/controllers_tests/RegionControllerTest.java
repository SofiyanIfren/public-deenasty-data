package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.RegionController;
import com.data.deenasty.deenasty_data.models.Region;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.RegionService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@WebMvcTest(RegionController.class)
@Import(SecurityConfig.class)
public class RegionControllerTest {

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
    private RegionService regionService;

    @Test
    void shouldReturnAllRegions() throws Exception {
        Region r1 = new Region(UUID.randomUUID(), "Europe", 10500000.0);
        Region r2 = new Region(UUID.randomUUID(), "Asia", 44579000.0);

        Mockito.when(regionService.getAllRegions()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/region/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnRegionById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Region r = new Region(id, "Africa", 30370000.0);
        Mockito.when(regionService.getRegionById(id)).thenReturn(Optional.of(r));

        mockMvc.perform(get("/region/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.name").value("Africa"))
            .andExpect(jsonPath("$.area").value(30370000.0));
    }

    @Test
    void shouldReturn404_WhenRegionNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(regionService.getRegionById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/region/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRegion() throws Exception {
        UUID id = UUID.randomUUID();
        Region r = new Region(id, "Oceania", 8600000.0);
        Mockito.when(regionService.createRegion(any(Region.class))).thenReturn(r);

        mockMvc.perform(post("/region").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "name": "Oceania",
                        "area": 8600000.0
                    }
                """.formatted(id)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.name").value("Oceania"))
            .andExpect(jsonPath("$.area").value(8600000.0));
    }

    @Test
    void shouldUpdateRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Region r = new Region(id, "Updated Region", 9999999.9);
        Mockito.when(regionService.updateRegion(eq(id), any(Region.class))).thenReturn(Optional.of(r));

        mockMvc.perform(put("/region/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "name": "Updated Region",
                        "area": 9999999.9
                    }
                """.formatted(id)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.name").value("Updated Region"))
            .andExpect(jsonPath("$.area").value(9999999.9));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentRegion() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(regionService.updateRegion(eq(id), any(Region.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/region/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s",
                        "name": "Ghostland",
                        "area": 0.0
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(regionService.deleteRegion(id)).thenReturn(true);

        mockMvc.perform(delete("/region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentRegion() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(regionService.deleteRegion(id)).thenReturn(false);

        mockMvc.perform(delete("/region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
