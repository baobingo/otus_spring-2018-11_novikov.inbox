package ru.otus.spring08.service;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring08.config.MongoConfig;
import ru.otus.spring08.repo.*;
import ru.otus.spring08.domain.Author;
import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Genre;
import ru.otus.spring08.domain.Review;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DataMongoTest
@Import({MongoConfig.class, SimpleLibraryService.class, SimpleSequenceService.class})
class SimpleLibraryServiceTest {

    @MockBean
    private BookRepo bookRepo;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SequenceService sequenceService;

    @BeforeEach
    void fill(){
        IntStream.range(0, 10).forEach(i ->{
            Book book = new Book("Book #" + i, new Author("Author #" + i), new Genre("Genre #" + i));
            book.setId(sequenceService.getNextSequence());
            mongoTemplate.insert(book);
        });
    }

    @AfterEach
    void clean() {
        mongoTemplate.dropCollection(Book.class);
    }


    @Test
    void bookCount() {
        when(bookRepo.count()).thenReturn(2L);
        assertEquals(2, libraryService.bookCount());
    }

    @Test
    void authorCount() {
        assertEquals(10, libraryService.authorCount());
    }

    @Test
    void genreCount() {
        assertEquals(10, libraryService.genreCount());
    }

    @Test
    void allBook() {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(mock(Book.class), mock(Book.class)));
        assertEquals(2, libraryService.allBook().size());
    }

    @Test
    void authorsBooks() {
        when(bookRepo.findBooksByAuthor_Name(any())).thenReturn(Arrays.asList(new Book("Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        assertEquals(1, libraryService.authorsBooks("Author #1").size());
    }

    @Test
    void genresBooks() {
        when(bookRepo.findBooksByGenre_Name(any())).thenReturn(Arrays.asList(new Book("Book #1", new Author("Author #1"), new Genre("Genre #1"))));
        assertEquals(1, libraryService.genresBooks("Genre #1").size());
    }

    @Test
    void addBook() {
        libraryService.addBook(new Book("Fairytale", new Author("Author #1"), new Genre("folk")));
        verify(bookRepo).insert((Book)any());
    }

    @Test
    void deleteBookById() {
        libraryService.deleteBookById(1);
        verify(bookRepo).deleteById(1L);
    }

    @Test
    void booksReviews() {
        Book mock = mock(Book.class);
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(mock));
        when(mock.getReviews()).thenReturn(Arrays.asList(mock(Review.class), mock(Review.class)));
        assertEquals(2, libraryService.booksReviews(anyLong()).size());
    }

    @Test
    void addReview() {
        when(bookRepo.findById(any())).thenReturn(Optional.of(mock(Book.class)));
        libraryService.addReview(mock(Review.class), 1L);
        verify(bookRepo).save(any());
    }
}