package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.ActivityController;
import com.data.deenasty.deenasty_data.models.Activity;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.ActivityService;
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


@WebMvcTest(ActivityController.class)
@Import(SecurityConfig.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public CustomUserDetailsService customUserDetailsService() {
            return Mockito.mock(CustomUserDetailsService.class);
        }
    }

    @MockitoBean
    private ActivityService activityService;

    @Test
    void shouldReturnAllActivities() throws Exception {
        Activity a1 = new Activity(UUID.randomUUID(), "Course");
        Activity a2 = new Activity(UUID.randomUUID(), "Lecture");

        Mockito.when(activityService.getAllActivities()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/activity/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Course"))
            .andExpect(jsonPath("$[1].name").value("Lecture"));
    }

    @Test
    void shouldReturnActivityById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Activity activity = new Activity(id, "Course");

        Mockito.when(activityService.getActivityById(id)).thenReturn(Optional.of(activity));

        mockMvc.perform(get("/activity/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Course"));
    }

    @Test
    void shouldReturn404_WhenActivityNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(activityService.getActivityById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/activity/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateActivity() throws Exception {
        Activity activity = new Activity(UUID.randomUUID(), "Course");

        Mockito.when(activityService.createActivity(any(Activity.class))).thenReturn(activity);

        mockMvc.perform(post("/activity").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Course"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Course"));
    }

    @Test
    void shouldUpdateActivity_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Activity updated = new Activity(id, "Course MAJ");

        Mockito.when(activityService.updateActivity(eq(id), any(Activity.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/activity/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Course MAJ"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Course MAJ"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentActivity() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(activityService.updateActivity(eq(id), any(Activity.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/activity/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "New"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteActivity_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(activityService.deleteActivity(id)).thenReturn(true);

        mockMvc.perform(delete("/activity/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentActivity() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(activityService.deleteActivity(id)).thenReturn(false);

        mockMvc.perform(delete("/activity/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
