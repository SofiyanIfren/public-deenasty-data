package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.DeenastyController;
import com.data.deenasty.deenasty_data.models.Deenasty;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.DeenastyService;
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

@WebMvcTest(DeenastyController.class)
@Import(SecurityConfig.class)
class DeenastyControllerTest {

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
    private DeenastyService deenastyService;

    @Test
    void shouldReturnAllDeenastys() throws Exception {
        Deenasty c1 = new Deenasty(UUID.randomUUID(), "Ommeyyads", "661", "750");
        Deenasty c2 = new Deenasty(UUID.randomUUID(), "Abbasids", "750", "1258");

        Mockito.when(deenastyService.getAllDeenasties()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/deenasty/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Ommeyyads"))
            .andExpect(jsonPath("$[1].name").value("Abbasids"));
    }

    @Test
    void shouldReturnDeenastyById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Deenasty deenasty = new Deenasty(UUID.randomUUID(), "Ommeyyads", "661", "750");

        Mockito.when(deenastyService.getDeenastyById(id)).thenReturn(Optional.of(deenasty));

        mockMvc.perform(get("/deenasty/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Ommeyyads"))
            .andExpect(jsonPath("$.startDate").value("661"))
            .andExpect(jsonPath("$.endDate").value("750"));
    }

    @Test
    void shouldReturn404_WhenDeenastyNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyService.getDeenastyById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/deenasty/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDeenasty() throws Exception {
        Deenasty deenasty = new Deenasty(UUID.randomUUID(), "Ommeyyads", "661", "750");

        Mockito.when(deenastyService.createDeenasty(any(Deenasty.class))).thenReturn(deenasty);

        mockMvc.perform(post("/deenasty").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Ommeyyads",
                        "startDate": "661",
                        "endDate": "750"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Ommeyyads"))
            .andExpect(jsonPath("$.startDate").value("661"))
            .andExpect(jsonPath("$.endDate").value("750"));
    }

    @Test
    void shouldUpdateDeenasty_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Deenasty updated = new Deenasty(UUID.randomUUID(), "Ommeyyads", "661", "750");

        Mockito.when(deenastyService.updateDeenasty(eq(id), any(Deenasty.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/deenasty/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Ommeyyads",
                        "startDate": "661",
                        "endDate": "750"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Ommeyyads"))
            .andExpect(jsonPath("$.startDate").value("661"))
            .andExpect(jsonPath("$.endDate").value("750"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentDeenasty() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyService.updateDeenasty(eq(id), any(Deenasty.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/deenasty/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Ommeyyads",
                        "startDate": "661",
                        "endDate": "750"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteDeenasty_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyService.deleteDeenasty(id)).thenReturn(true);

        mockMvc.perform(delete("/deenasty/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentDeenasty() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(deenastyService.deleteDeenasty(id)).thenReturn(false);

        mockMvc.perform(delete("/deenasty/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
