package com.data.deenasty.deenasty_data.controllers_tests;

import com.data.deenasty.deenasty_data.controllers.BookController;
import com.data.deenasty.deenasty_data.models.Book;
import com.data.deenasty.deenasty_data.security.CustomUserDetailsService;
import com.data.deenasty.deenasty_data.security.SecurityConfig;
import com.data.deenasty.deenasty_data.services.BookService;
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

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
class BookControllerTest {

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
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        Book b1 = new Book(UUID.randomUUID(), "Title1", LocalDate.of(2020, 5, 20), "Description1");
        Book b2 = new Book(UUID.randomUUID(), "Title2", LocalDate.of(2020, 5, 21), "Description2");

        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(b1, b2));

        mockMvc.perform(get("/book/all").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("Title1"))
            .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    void shouldReturnBookById_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Book book = new Book(id, "Title1", LocalDate.of(2020, 5, 20), "Description1");

        Mockito.when(bookService.getBookById(id)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/book/" + id).with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Title1"))
            .andExpect(jsonPath("$.publicationDate").value("2020-05-20"))
            .andExpect(jsonPath("$.description").value("Description1"));
    }

    @Test
    void shouldReturn404_WhenBookNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(bookService.getBookById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/book/" + id).with(anonymous()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateBook() throws Exception {
        Book book = new Book(UUID.randomUUID(), "New Book",LocalDate.of(2020, 5, 20), "New description");

        Mockito.when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/book").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "New Book",
                        "description": "New description"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("New Book"))
            //TODO : .andExpect(jsonPath("$.publicationDate").value("2020-05-20"))
            .andExpect(jsonPath("$.description").value("New description"));
    }

    @Test
    void shouldUpdateBook_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        Book updated = new Book(id, "Updated Book",LocalDate.of(2020, 5, 20), "Updated description");

        Mockito.when(bookService.updateBook(eq(id), any(Book.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/book/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "Updated Book",
                        "description": "Updated description"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Book"))
            //TODO : .andExpect(jsonPath("$.publicationDate").value("2020-05-20"))
            .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    void shouldReturn404_WhenUpdatingNonexistentBook() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(bookService.updateBook(eq(id), any(Book.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/book/" + id).with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "Doesn't Exist",
                        "description": "Unknown"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBook_WhenExists() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(bookService.deleteBook(id)).thenReturn(true);

        mockMvc.perform(delete("/book/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404_WhenDeletingNonexistentBook() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(bookService.deleteBook(id)).thenReturn(false);

        mockMvc.perform(delete("/book/" + id).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
