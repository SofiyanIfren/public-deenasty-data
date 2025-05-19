package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.CityController;
import com.data.deenasty.deenasty_data.models.City;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.CityService;
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

@WebMvcTest(CityController.class)
@Import(SecurityConfig.class)
class CityControllerTest {

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
    private CityService cityService;

    @Test
    void shouldReturnAllCities() throws Exception {
        City c1 = new City(UUID.randomUUID(), "Paris", "Lutece");
        City c2 = new City(UUID.randomUUID(), "Marseille", "Massillia");

        Mockito.when(cityService.getAllCities()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/city/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Paris"))
            .andExpect(jsonPath("$[1].name").value("Marseille"));
    }

    @Test
    void shouldReturnCityById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        City city = new City(UUID.randomUUID(), "Marseille", "Massillia");

        Mockito.when(cityService.getCityById(id)).thenReturn(Optional.of(city));

        mockMvc.perform(get("/city/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Marseille"));
    }

    @Test
    void shouldReturn404_WhenCityNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(cityService.getCityById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/city/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCity() throws Exception {
        City city = new City(UUID.randomUUID(), "Marseille", "Massillia");

        Mockito.when(cityService.createCity(any(City.class))).thenReturn(city);

        mockMvc.perform(post("/city").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Marseille"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Marseille"));
    }

    @Test
    void shouldUpdateCity_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        City updated = new City(UUID.randomUUID(), "Marseille", "Massillia");

        Mockito.when(cityService.updateCity(eq(id), any(City.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/city/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Marseille"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Marseille"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentCity() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(cityService.updateCity(eq(id), any(City.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/city/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Unknown City"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCity_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(cityService.deleteCity(id)).thenReturn(true);

        mockMvc.perform(delete("/city/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentCity() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(cityService.deleteCity(id)).thenReturn(false);

        mockMvc.perform(delete("/city/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
