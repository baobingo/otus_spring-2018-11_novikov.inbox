package ru.otus.spring10.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring10.domain.Author;
import ru.otus.spring10.domain.Book;
import ru.otus.spring10.domain.Genre;
import ru.otus.spring10.domain.Review;
import ru.otus.spring10.service.SequenceService;
import ru.otus.spring10.service.SimpleSequenceService;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(SimpleSequenceService.class)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private SequenceService sequenceService;

    @BeforeEach
    void fill(){
        IntStream.range(0, 10).forEach(i ->{
            Book book = new Book("Book #" + i, new Author("Author #" + i), new Genre("Genre #" + i));
            book.setId(sequenceService.getNextSequence());
            IntStream.range(0, 10).forEach(x ->{book.addReview(new Review(sequenceService.getNextSequence(),"Author #" + x, "Review body"));});
            bookRepo.insert(book);
        });
    }

    @AfterEach
    void clean() {
        bookRepo.deleteAll();
    }

    @Test
    void findAllBook(){
        assertEquals(10, bookRepo.findAll().size());
    }

    @Test
    void findReview(){
        assertEquals(10, bookRepo.findAll().get(0).getReviews().size());
    }
}