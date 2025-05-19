package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.EventController;
import com.data.deenasty.deenasty_data.models.Event;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.EventService;
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

@WebMvcTest(EventController.class)
@Import(SecurityConfig.class)
class EventControllerTest {

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
    private EventService eventService;

    @Test
    void shouldReturnAllEvents() throws Exception {
        Event e1 = new Event(UUID.randomUUID(), "Birth of Prophet Muhammed (p) at Mecca", "570", "570");
        Event e2 = new Event(UUID.randomUUID(), "Revelation of the first verses of the Qur’an", "610", "610");

        Mockito.when(eventService.getAllEvents()).thenReturn(List.of(e1, e2));

        mockMvc.perform(get("/event/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].description").value("Birth of Prophet Muhammed (p) at Mecca"))
            .andExpect(jsonPath("$[1].description").value("Revelation of the first verses of the Qur’an"));
    }

    @Test
    void shouldReturnEventById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Event event = new Event(UUID.randomUUID(), "Birth of Prophet Muhammed (p) at Mecca", "570", "570");

        Mockito.when(eventService.getEventById(id)).thenReturn(Optional.of(event));

        mockMvc.perform(get("/event/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Birth of Prophet Muhammed (p) at Mecca"))
            .andExpect(jsonPath("$.startYear").value("570"))
            .andExpect(jsonPath("$.endYear").value("570"));
    }

    @Test
    void shouldReturn404_WhenEventNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(eventService.getEventById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/event/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateEvent() throws Exception {
        Event event = new Event(UUID.randomUUID(), "Birth of Prophet Muhammed (p) at Mecca", "570", "570");

        Mockito.when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/event").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "Birth of Prophet Muhammed (p) at Mecca",
                        "startYear": "570",
                        "endYear": "570"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description").value("Birth of Prophet Muhammed (p) at Mecca"))
            .andExpect(jsonPath("$.startYear").value("570"))
            .andExpect(jsonPath("$.endYear").value("570"));
    }

    @Test
    void shouldUpdateEvent_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Event updated = new Event(UUID.randomUUID(), "Birth of Prophet Muhammed (p) at Mecca", "570", "570");

        Mockito.when(eventService.updateEvent(eq(id), any(Event.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/event/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "Birth of Prophet Muhammed (p) at Mecca",
                        "startYear": "570",
                        "endYear": "570"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Birth of Prophet Muhammed (p) at Mecca"))
            .andExpect(jsonPath("$.startYear").value("570"))
            .andExpect(jsonPath("$.endYear").value("570"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentEvent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(eventService.updateEvent(eq(id), any(Event.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/event/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "Birth of Prophet Muhammed (p) at Mecca",
                        "startYear": "570",
                        "endYear": "570"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteEvent_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(eventService.deleteEvent(id)).thenReturn(true);

        mockMvc.perform(delete("/event/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentEvent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(eventService.deleteEvent(id)).thenReturn(false);

        mockMvc.perform(delete("/event/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
