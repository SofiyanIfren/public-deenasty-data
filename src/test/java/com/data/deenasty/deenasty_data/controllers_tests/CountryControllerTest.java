package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.CountryController;
import com.data.deenasty.deenasty_data.models.Country;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.CountryService;
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

@WebMvcTest(CountryController.class)
@Import(SecurityConfig.class)
class CountryControllerTest {

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
    private CountryService countryService;

    @Test
    void shouldReturnAllCountries() throws Exception {
        Country c1 = new Country(UUID.randomUUID(), "France", "Gaule", 543940.0);
        Country c2 = new Country(UUID.randomUUID(), "Italy", "Donno", 302073.0);

        Mockito.when(countryService.getAllCountries()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/country/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("France"))
            .andExpect(jsonPath("$[1].name").value("Italy"));
    }

    @Test
    void shouldReturnCountryById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Country country = new Country(UUID.randomUUID(), "France", "Gaule", 543940.0);

        Mockito.when(countryService.getCountryById(id)).thenReturn(Optional.of(country));

        mockMvc.perform(get("/country/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("France"));
    }

    @Test
    void shouldReturn404_WhenCountryNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryService.getCountryById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/country/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCountry() throws Exception {
        Country country = new Country(UUID.randomUUID(), "France", "Gaule", 543940.0);

        Mockito.when(countryService.createCountry(any(Country.class))).thenReturn(country);

        mockMvc.perform(post("/country").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "France"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("France"));
    }

    @Test
    void shouldUpdateCountry_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Country updated = new Country(UUID.randomUUID(), "France", "Gaule", 543940.0);

        Mockito.when(countryService.updateCountry(eq(id), any(Country.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/country/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "France"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("France"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentCountry() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryService.updateCountry(eq(id), any(Country.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/country/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Atlantis"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCountry_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryService.deleteCountry(id)).thenReturn(true);

        mockMvc.perform(delete("/country/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentCountry() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryService.deleteCountry(id)).thenReturn(false);

        mockMvc.perform(delete("/country/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
