package ru.otus.spring07.repo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private GenreRepo genreRepo;

    @Test
    void findByAuthor(){
        assertEquals(1, bookRepo.findByAuthor(authorRepo.findByName("Author #1").get()).size());
    }

    @Test
    void findByGenre(){
        assertEquals(1, bookRepo.findByGenre(genreRepo.findByName("Genre #1").get()).size());
    }

    @Test
    void findAll(){
        assertEquals(2, bookRepo.findAll().size());
    }

}