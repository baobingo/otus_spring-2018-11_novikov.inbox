package ru.otus.spring08.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring08.config.MongoConfig;
import ru.otus.spring08.domain.*;
import ru.otus.spring08.service.SequenceService;
import ru.otus.spring08.service.SimpleSequenceService;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({MongoConfig.class, SimpleSequenceService.class})
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private SequenceService sequenceService;

    @BeforeEach
    void fill(){
        IntStream.range(0, 10).forEach(i ->{
            Book book = new Book("Book #" + i, new Author("Author #" + i), new Genre("Genre #" + i));
            book.setId(sequenceService.getNextSequence());
            bookRepo.insert(book);
        });
    }

    @AfterEach
    void clean() {
        bookRepo.deleteAll();
    }

    @Test
    void findBooksByAuthor_Name(){
        assertEquals(1, bookRepo.findBooksByAuthor_Name("Author #1").size());
        assertEquals(1, bookRepo.findBooksByAuthor_Name("Author #2").size());
        assertEquals(1, bookRepo.findBooksByAuthor_Name("Author #3").size());
    }

    @Test
    void findBooksBy_Name(){
        assertEquals(1, bookRepo.findBooksByGenre_Name("Genre #1").size());
        assertEquals(1, bookRepo.findBooksByGenre_Name("Genre #2").size());
        assertEquals(1, bookRepo.findBooksByGenre_Name("Genre #3").size());
    }
}