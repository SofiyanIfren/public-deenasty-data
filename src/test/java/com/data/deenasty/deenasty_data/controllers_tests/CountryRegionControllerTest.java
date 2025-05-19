package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.CountryRegionController;
import com.data.deenasty.deenasty_data.models.CountryRegion;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.CountryRegionService;
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

@WebMvcTest(CountryRegionController.class)
@Import(SecurityConfig.class)
class CountryRegionControllerTest {

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
    private CountryRegionService countryRegionService;

    @Test
    void shouldReturnAllCountryRegions() throws Exception {
        CountryRegion cr1 = new CountryRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));
        CountryRegion cr2 = new CountryRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 21), LocalDate.of(2020, 5, 21));

        Mockito.when(countryRegionService.getAllCounrtyRegions()).thenReturn(List.of(cr1, cr2));

        mockMvc.perform(get("/country-region/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnCountryRegionById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        CountryRegion cr1 = new CountryRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(countryRegionService.getCountryRegionById(id)).thenReturn(Optional.of(cr1));

        mockMvc.perform(get("/country-region/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.startDate").value("2020-05-20"))
            .andExpect(jsonPath("$.endDate").value("2020-05-21"));

    }

    @Test
    void shouldReturn404_WhenCountryRegionNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryRegionService.getCountryRegionById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/country-region/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCountryRegion() throws Exception {
        CountryRegion cr1 = new CountryRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(countryRegionService.createCountryRegion(any(CountryRegion.class))).thenReturn(cr1);

        mockMvc.perform(post("/country-region").with(user("admin").roles("ADMIN"))
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
    void shouldUpdateCountryRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        CountryRegion updated = new CountryRegion(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(countryRegionService.updateCountryRegion(eq(id), any(CountryRegion.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/country-region/" + id).with(user("admin").roles("ADMIN"))
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
    void shouldReturn404_WhenUpdatingNonexistentCountryRegion() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryRegionService.updateCountryRegion(eq(id), any(CountryRegion.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/country-region/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "startDate": "2020-05-18",
                        "endDate": "2020-05-19"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCountryRegion_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryRegionService.deleteCountryRegion(id)).thenReturn(true);

        mockMvc.perform(delete("/country-region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentCountryRegion() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(countryRegionService.deleteCountryRegion(id)).thenReturn(false);

        mockMvc.perform(delete("/country-region/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
