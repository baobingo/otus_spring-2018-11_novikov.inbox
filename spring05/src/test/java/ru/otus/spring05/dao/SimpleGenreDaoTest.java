package ru.otus.spring05.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring05.domain.Genre;

import java.util.List;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleGenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    void count() {
        Assert.assertEquals(2, genreDao.count());
    }

    @Test
    void insert() {
        genreDao.insert(new Genre(3, "Author #3"));
        Assert.assertEquals(3, genreDao.count());
    }

    @Test
    void deleteById() {
        genreDao.deleteById(2);
        Assert.assertEquals(1, genreDao.count());
    }

    @Test
    void getAll() {
        List<Genre> authors = genreDao.getAll();
        Assert.assertEquals("Genre #1", authors.get(0).getName());
    }

    @Test
    void getByID() {
        Assert.assertEquals("Genre #2", genreDao.getByID(2).getName());
    }
}