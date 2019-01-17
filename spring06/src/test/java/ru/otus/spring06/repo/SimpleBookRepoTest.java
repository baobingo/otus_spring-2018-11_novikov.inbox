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
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Genre;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({SimpleBookRepo.class, SimpleAuthorRepo.class, SimpleGenreRepo.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleBookRepoTest {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private GenreRepo genreRepo;

    @Test
    void count() {
        Assert.assertEquals(2, bookRepo.count());
    }

    @Test
    void insert() {
        Author author = authorRepo.getByID(1);
        Genre genre = genreRepo.getByID(2);
        System.out.println(genre.getId());
        Book book = new Book("Book #3", author, genre);
        bookRepo.insert(book);
        Assert.assertEquals(3, bookRepo.count());
    }

    @Test
    void deleteById() {
        bookRepo.deleteById(2);
        Assert.assertEquals(1, bookRepo.count());
    }

    @Test
    void getAll() {
        List<Book> bookList = bookRepo.getAll();
        Assert.assertEquals(2, bookList.size());
        Assert.assertEquals( "Book #1", bookList.get(0).getName());
    }

    @Test
    void getByID() {
        Book book = bookRepo.getByID(2);
        Assert.assertEquals("Book #2", book.getName());
    }

    @Test
    void getByAuthor() {
        Author author =  authorRepo.getByID(2);
        List<Book> books = bookRepo.getByAuthor(author);
        Assert.assertEquals("Book #2", books.get(0).getName());
        Assert.assertEquals(1, books.size());

        Genre genre = genreRepo.getByID(2);
        bookRepo.insert(new Book("Book #3", author, genre));
        books = bookRepo.getByAuthor(author);
        Assert.assertEquals(2, books.size());
    }

    @Test
    void getByGenre() {
        Genre genre = new Genre("Genre #3");
        genreRepo.insert(genre);

        bookRepo.insert(new Book("Book #3", authorRepo.getByID(1), genre));
        Assert.assertEquals(3, bookRepo.count());

        List<Book> books = bookRepo.getByGenre(genre);
        Assert.assertEquals(1, books.size());
        Assert.assertEquals("Book #3", books.get(0).getName());
    }
}