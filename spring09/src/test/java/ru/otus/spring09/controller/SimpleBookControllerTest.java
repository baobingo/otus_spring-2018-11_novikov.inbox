package ru.otus.spring09.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring09.domain.Author;
import ru.otus.spring09.domain.Book;
import ru.otus.spring09.domain.Genre;
import ru.otus.spring09.repository.BookRepository;
import ru.otus.spring09.service.SequenceService;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

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
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    void main() throws Exception{
        when(bookRepository.findAll()).thenReturn(Arrays.asList(new Book("Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        mockMvc.perform(get("/books")).andExpect(view().name("books")).andExpect(model().attribute("books", hasSize(1))).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception{
        mockMvc.perform(post("/books/delete/?id=1")).andExpect(status().is3xxRedirection());
        verify(bookRepository).deleteById(anyLong());
    }

    @Test
    void addBook() throws Exception{
        //I don't know how)) I dont' want to use PowerMockito for whenNew() construction
    }

    @Test
    void actionAdd() throws Exception{
        mockMvc.perform(post("/books/add")).andExpect(status().is3xxRedirection());
        verify(bookRepository).insert(any(Book.class));
    }

    @Test
    void editBook() throws Exception{
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book("Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        mockMvc.perform(get("/books/edit/?id=1")).andExpect(view().name("editbook")).andExpect(model().attributeExists("book")).andExpect(status().isOk());
    }

    @Test
    void editBook1() throws Exception{
        mockMvc.perform(post("/books/edit")).andExpect(status().is3xxRedirection());
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void review() throws Exception{
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        mockMvc.perform(get("/books/review/").param("id", "1")).andExpect(view().name("review")).andExpect(model().attributeExists("reviews")).andExpect(status().isOk());
    }
}