package ru.otus.spring05.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring05.domain.Author;

import java.util.List;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleAuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void count() {
        Assert.assertEquals(2, authorDao.count());
    }

    @Test
    void insert() {
        authorDao.insert(new Author(3, "Author #3"));
        Assert.assertEquals(3, authorDao.count());
    }

    @Test
    void deleteById() {
        authorDao.deleteById(2);
        Assert.assertEquals(1, authorDao.count());
    }

    @Test
    void getAll() {
        List<Author> authors = authorDao.getAll();
        Assert.assertEquals("Author #1", authors.get(0).getName());
    }

    @Test
    void getByID() {
        Assert.assertEquals("Author #2", authorDao.getByID(2).getName());
    }
}