package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.HistoricalFigureBookController;
import com.data.deenasty.deenasty_data.models.HistoricalFigureBook;
import com.data.deenasty.deenasty_data.services.HistoricalFigureBookService;
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

@WebMvcTest(HistoricalFigureBookController.class)
@Import(SecurityConfig.class)
class HistoricalFigureBookControllerTest {

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
    private HistoricalFigureBookService historicalFigureBookService;

    @Test
    void shouldReturnAllHistoricalFigureBooks() throws Exception {
        HistoricalFigureBook hfb1 = new HistoricalFigureBook(UUID.randomUUID(), null, null);
        HistoricalFigureBook hfb2 = new HistoricalFigureBook(UUID.randomUUID(), null, null);

        Mockito.when(historicalFigureBookService.getAllHistoricalFigureBooks()).thenReturn(List.of(hfb1, hfb2));

        mockMvc.perform(get("/historicalfigure-book/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnHistoricalFigureBookById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureBook hfb = new HistoricalFigureBook(id, null, null);

        Mockito.when(historicalFigureBookService.getHistoricalFigureBookById(id)).thenReturn(Optional.of(hfb));

        mockMvc.perform(get("/historicalfigure-book/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturn404_WhenHistoricalFigureBookNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureBookService.getHistoricalFigureBookById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/historicalfigure-book/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHistoricalFigureBook() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureBook hfb = new HistoricalFigureBook(id, null, null);

        Mockito.when(historicalFigureBookService.createHistoricalFigureBook(any(HistoricalFigureBook.class))).thenReturn(hfb);

        mockMvc.perform(post("/historicalfigure-book").with(user("admin").roles("ADMIN"))
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
    void shouldUpdateHistoricalFigureBook_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        HistoricalFigureBook updated = new HistoricalFigureBook(id, null, null);

        Mockito.when(historicalFigureBookService.updateHistoricalFigureBook(eq(id), any(HistoricalFigureBook.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/historicalfigure-book/" + id).with(user("admin").roles("ADMIN"))
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
    void shouldReturn404_WhenUpdatingNonexistentHistoricalFigureBook() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureBookService.updateHistoricalFigureBook(eq(id), any(HistoricalFigureBook.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/historicalfigure-book/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id": "%s"
                    }
                """.formatted(id)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteHistoricalFigureBook_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureBookService.deleteHistoricalFigureBook(id)).thenReturn(true);

        mockMvc.perform(delete("/historicalfigure-book/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentHistoricalFigureBook() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(historicalFigureBookService.deleteHistoricalFigureBook(id)).thenReturn(false);

        mockMvc.perform(delete("/historicalfigure-book/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
