package ru.otus.spring07.repo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    void findByName(){
        assertEquals("Author #1", authorRepo.findByName("Author #1").get().getName());
    }

}