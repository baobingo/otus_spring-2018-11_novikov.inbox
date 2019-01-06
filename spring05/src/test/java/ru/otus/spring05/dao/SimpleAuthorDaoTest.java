package ru.otus.spring05.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring05.domain.Author;

import java.util.List;

@ExtendWith({SpringExtension.class})
@JdbcTest
@Import({SimpleAuthorDao.class})
@Sql({"/schema-test.sql", "/data-test.sql"})
class SimpleAuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void count() {
        Assert.assertEquals(2, authorDao.count());
    }

    @Test
    void insert() {
        Author author = new Author("Author #3");
        authorDao.insert(author);
        Assert.assertEquals(3, authorDao.count());
        Assert.assertEquals(3, author.getId());
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

    @Test
    void getByName() {
        Author author = authorDao.getByName("Author #1");
        Assert.assertEquals("Author #1", author.getName());
    }

    @Test
    void insertOrId() {
        Author author =  new Author("Author #1");
        Assert.assertEquals(-1, author.getId());
        authorDao.insertOrId(author);
        Assert.assertEquals(1, author.getId());
    }
}