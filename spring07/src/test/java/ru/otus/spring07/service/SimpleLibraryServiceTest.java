package ru.otus.spring07.service;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring07.repo.*;
import ru.otus.spring07.domain.Author;
import ru.otus.spring07.domain.Book;
import ru.otus.spring07.domain.Genre;
import ru.otus.spring07.domain.Review;
import ru.otus.spring07.ui.SimpleHelper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DataJpaTest
@Import({SimpleLibraryService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleLibraryServiceTest {

    @MockBean
    private AuthorRepo authorRepo;
    @MockBean
    private GenreRepo genreRepo;
    @MockBean
    private BookRepo bookRepo;
    @MockBean
    private ReviewRepo reviewRepo;

    @Autowired
    private LibraryService libraryService;

    @Test
    void bookCount() {
        when(bookRepo.count()).thenReturn(2L);
        assertEquals(2, libraryService.bookCount());
    }

    @Test
    void authorCount() {
        when(authorRepo.count()).thenReturn(2L);
        assertEquals(2, libraryService.authorCount());
    }

    @Test
    void genreCount() {
        when(genreRepo.count()).thenReturn(2L);
        assertEquals(2, libraryService.genreCount());
    }

    @Test
    void allBook() {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(new Book(1, "Book #1", new Author("Author #1"), new Genre("Genre #1")), new Book(2, "Book #2", new Author("Author #2"), new Genre("Genre #2"))));
        assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n" +
                "Book #2 ID: 2 Author: Author #2 Genre: Genre #2\n", SimpleHelper.booksToText(libraryService.allBook()));
    }

    @Test
    void authorsBooks() {
        when(authorRepo.findByName(anyString())).thenReturn(Optional.of(new Author("Author #1")));
        when(bookRepo.findByAuthor(any())).thenReturn(Arrays.asList(new Book(1, "Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n", SimpleHelper.booksToText(libraryService.authorsBooks("Author #1")));
    }

    @Test
    void genresBooks() {
        when(genreRepo.findByName(anyString())).thenReturn(Optional.of(new Genre("Genre #1")));
        when(bookRepo.findByGenre(any())).thenReturn(Arrays.asList(new Book(1, "Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n", SimpleHelper.booksToText(libraryService.genresBooks("Genre #1")));
    }

    @Test
    void addBook() {
        libraryService.addBook(new Book("Fairytale", new Author("Author #1"), new Genre("folk")));
        verify(bookRepo).save(any());
    }

    @Test
    void addAuthor() {
        libraryService.addAuthor(new Author("Author #3"));
        verify(authorRepo).save(any());
    }

    @Test
    void addGenre() {
        libraryService.addGenre(new Genre("Genre #3"));
        verify(genreRepo).save(any());
    }

    @Test
    void deleteBookById() {
        libraryService.deleteBookById(1);
        verify(bookRepo).deleteById(1L);
    }

    @Test
    void deleteAuthorById() {
        libraryService.deleteAuthorById(2);
        verify(authorRepo).deleteById(2L);
    }

    @Test
    void deleteGenreById() {
        libraryService.deleteGenreById(1);
        verify(genreRepo).deleteById(1L);
    }

    @Test
    void booksReviews() {
        when(bookRepo.findById(any())).thenReturn(Optional.of(mock(Book.class)));
        when(reviewRepo.findByBook(any())).thenReturn(Arrays.asList(mock(Review.class)));
        assertEquals(1, libraryService.booksReviews(1L).size());
    }

    @Test
    void addReview() {
        when(bookRepo.findById(any())).thenReturn(Optional.of(mock(Book.class)));
        libraryService.addReview(mock(Review.class), 1L);
        verify(reviewRepo).save(any());
    }
}