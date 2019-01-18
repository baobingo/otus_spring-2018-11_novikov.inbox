package ru.otus.spring06.repo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring06.domain.Genre;

import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({SimpleGenreRepo.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleGenreRepoTest {

    @Autowired
    private GenreRepo genreRepo;

    @Test
    void count() {
        Assert.assertEquals(2, genreRepo.count());
    }

    @Test
    void insert() {
        Genre genre = new Genre("Genre #3");
        genreRepo.insert(genre);
        Assert.assertEquals(3, genreRepo.count());
        Assert.assertEquals(3, genre.getId());
    }

    @Test
    void deleteById() {
        genreRepo.insert(new Genre("Genre #3"));
        genreRepo.deleteById(3);
        Assert.assertEquals(2, genreRepo.count());
    }

    @Test
    void getAll() {
        List<Genre> authors = genreRepo.getAll();
        Assert.assertEquals("Genre #1", authors.get(0).getName());
    }

    @Test
    void getByID() {
        Assert.assertEquals(true, genreRepo.getByID(2).isPresent());
    }

    @Test
    void getByName() {
        Assert.assertEquals(true, genreRepo.getByName("Genre #1").isPresent());
    }

    @Test
    void insertOrId() {
        Genre genre = new Genre("Genre #1");
        Assert.assertEquals(-1, genre.getId());
        genreRepo.insertOrId(genre);
        Assert.assertEquals(1, genre.getId());
        genre = new Genre("Genre #3");
        Assert.assertEquals(-1, genre.getId());
        genreRepo.insertOrId(genre);
        Assert.assertEquals(3, genre.getId());
    }
}