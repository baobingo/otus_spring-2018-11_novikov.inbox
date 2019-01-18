package ru.otus.spring06.repo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring06.domain.Author;

import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({SimpleAuthorRepo.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleAuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    void count() {
        Assert.assertEquals(2, authorRepo.count());
    }

    @Test
    void insert() {
        Author author = new Author("Author #3");
        authorRepo.insert(author);
        Assert.assertEquals(3, authorRepo.count());
        Assert.assertEquals(3, author.getId());
    }

    @Test
    void deleteById() {
        authorRepo.insert(new Author("Author #3"));
        authorRepo.deleteById(3);
        Assert.assertEquals(2, authorRepo.count());
    }

    @Test
    void getAll() {
        List<Author> authors = authorRepo.getAll();
        Assert.assertEquals("Author #1", authors.get(0).getName());
    }

    @Test
    void getByID() {
        Assert.assertEquals(true, authorRepo.getByID(2).isPresent());
    }

    @Test
    void getByName() {
        Assert.assertEquals(true, authorRepo.getByName("Author #1").isPresent());
        Assert.assertEquals(false, authorRepo.getByName("Author #5").isPresent());
    }

    @Test
    void insertOrId() {
        Author author = new Author("Author #1");
        Assert.assertEquals(-1, author.getId());
        authorRepo.insertOrId(author);
        Assert.assertEquals(1, author.getId());
        author = new Author("Author #3");
        Assert.assertEquals(-1, author.getId());
        authorRepo.insertOrId(author);
        Assert.assertEquals(3, author.getId());
    }
}