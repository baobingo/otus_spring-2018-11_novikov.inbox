package ru.otus.spring10.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring10.domain.Author;
import ru.otus.spring10.domain.Book;
import ru.otus.spring10.domain.Genre;
import ru.otus.spring10.repository.BookRepository;
import ru.otus.spring10.service.SequenceService;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SimpleBookController.class)
class SimpleBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private SequenceService sequenceService;


    @Test
    void index() throws Exception{
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void main() throws Exception{
        when(bookRepository.findAll()).thenReturn(Arrays.asList(new Book("Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        mockMvc.perform(get("/api/books").contentType(APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(1))).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception{
        mockMvc.perform(delete("/api/books/1")).andExpect(status().isOk());
        verify(bookRepository).findById(1L);
    }

    @Test
    void addBook() throws Exception{
        //I don't know how)) I dont' want to use PowerMockito for whenNew() construction
    }

    @Test
    void actionAdd() throws Exception{
        mockMvc.perform(post("/api/books")).andExpect(status().isOk());
        verify(bookRepository).insert(any(Book.class));
    }

    @Test
    void editBook() throws Exception{
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book("Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        mockMvc.perform(get("/api/books/1").contentType(APPLICATION_JSON)).andExpect(jsonPath("$.name", is("Book #1"))).andExpect(status().isOk());
    }

    @Test
    void editBook1() throws Exception{
        mockMvc.perform(put("/api/books")).andExpect(status().isOk());
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void review() throws Exception{
        mockMvc.perform(get("/api/books/1/reviews").contentType(APPLICATION_JSON)).andExpect(jsonPath("$", is("Error"))).andExpect(status().isOk());
        verify(bookRepository).findById(anyLong());
    }
}