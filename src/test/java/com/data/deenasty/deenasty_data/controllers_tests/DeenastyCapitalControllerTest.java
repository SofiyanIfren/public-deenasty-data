package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.DeenastyCapitalController;
import com.data.deenasty.deenasty_data.models.DeenastyCapital;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.DeenastyCapitalService;
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

@WebMvcTest(DeenastyCapitalController.class)
@Import(SecurityConfig.class)
class DeenastyCapitalControllerTest {

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
    private DeenastyCapitalService deenastyCapitalService;

    @Test
    void shouldReturnAllDeenastyCapitals() throws Exception {
        DeenastyCapital c1 = new DeenastyCapital(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));
        DeenastyCapital c2 = new DeenastyCapital(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyCapitalService.getAllDeenastyCapitals()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/deenasty-capital/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].startDate").value("2020-05-20"))
            .andExpect(jsonPath("$[1].startDate").value("2020-05-20"));
    }

    @Test
    void shouldReturnDeenastyCapitalById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        DeenastyCapital capital = new DeenastyCapital(id, LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyCapitalService.getDeenastyCapitalById(id)).thenReturn(Optional.of(capital));

        mockMvc.perform(get("/deenasty-capital/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.startDate").value("2020-05-20"))
            .andExpect(jsonPath("$.endDate").value("2020-05-21"));
    }

    @Test
    void shouldReturn404_WhenDeenastyCapitalNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyCapitalService.getDeenastyCapitalById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/deenasty-capital/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDeenastyCapital() throws Exception {
        DeenastyCapital capital = new DeenastyCapital(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyCapitalService.createDeenastyCapital(any(DeenastyCapital.class))).thenReturn(capital);

        mockMvc.perform(post("/deenasty-capital").with(user("admin").roles("ADMIN"))
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
    void shouldUpdateDeenastyCapital_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        DeenastyCapital updated = new DeenastyCapital(UUID.randomUUID(), LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 21));

        Mockito.when(deenastyCapitalService.updateDeenastyCapital(eq(id), any(DeenastyCapital.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/deenasty-capital/" + id).with(user("admin").roles("ADMIN"))
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
    void shouldReturn404_WhenUpdatingNonexistentDeenastyCapital() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyCapitalService.updateDeenastyCapital(eq(id), any(DeenastyCapital.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/deenasty-capital/" + id).with(user("admin").roles("ADMIN"))
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
    void shouldDeleteDeenastyCapital_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyCapitalService.deleteDeenastyCapital(id)).thenReturn(true);

        mockMvc.perform(delete("/deenasty-capital/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentDeenastyCapital() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyCapitalService.deleteDeenastyCapital(id)).thenReturn(false);

        mockMvc.perform(delete("/deenasty-capital/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
