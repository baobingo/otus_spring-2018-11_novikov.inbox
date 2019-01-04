package ru.otus.spring05.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring05.domain.Genre;

import java.util.List;

@ExtendWith({SpringExtension.class})
@JdbcTest
@Import({SimpleGenreDao.class})
class SimpleGenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    void count() {
        Assert.assertEquals(2, genreDao.count());
    }

    @Test
    void insert() {
        Genre genre = new Genre("Genre #3");
        genreDao.insert(genre);
        Assert.assertEquals(3, genreDao.count());
        Assert.assertEquals(3, genre.getId());
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

    @Test
    void getByName() {
        Genre genre = genreDao.getByName("Genre #1");
        Assert.assertEquals("Genre #1", genre.getName());
    }
}