package ru.otus.spring05.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring05.domain.Author;
import ru.otus.spring05.domain.Book;
import ru.otus.spring05.domain.Genre;

import java.util.List;

@ExtendWith({SpringExtension.class})
@JdbcTest
@Import({SimpleBookDao.class, SimpleGenreDao.class, SimpleAuthorDao.class})
class SimpleBookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private GenreDao genreDao;

    @Test
    void count() {
        Assert.assertEquals(2, bookDao.count());
    }

    @Test
    void insert() {
        Author author = authorDao.getByID(1);
        Genre genre = genreDao.getByID(2);

        Book book = new Book("Book #3", author, genre);
        bookDao.insert(book);
        Assert.assertEquals(3, bookDao.count());
    }

    @Test
    void deleteById() {
        bookDao.deleteById(2);
        Assert.assertEquals(1, bookDao.count());
        authorDao.deleteById(1);
        Assert.assertEquals(0, bookDao.count());
    }

    @Test
    void getAll() {
        List<Book> bookList = bookDao.getAll();
        Assert.assertEquals(2, bookList.size());
        Assert.assertEquals( "Book #1", bookList.get(0).getName());
    }

    @Test
    void getByID() {
        Book book = bookDao.getByID(2);
        Assert.assertEquals("Book #2", book.getName());
    }

    @Test
    void getByAuthor() {
        Author author =  authorDao.getByID(2);
        List<Book> books = bookDao.getByAuthor(author);
        Assert.assertEquals("Book #2", books.get(0).getName());
        Assert.assertEquals(1, books.size());

        Genre genre = genreDao.getByID(2);
        bookDao.insert(new Book("Book #3", author, genre));
        books = bookDao.getByAuthor(author);
        Assert.assertEquals(2, books.size());
    }

    @Test
    void getByGenre() {
        Genre genre = new Genre("Genre #3");
        genreDao.insert(genre);

        bookDao.insert(new Book("Book #3", authorDao.getByID(1), genre));
        Assert.assertEquals(3, bookDao.count());

        List<Book> books = bookDao.getByGenre(genre);
        Assert.assertEquals(1, books.size());
        Assert.assertEquals("Book #3", books.get(0).getName());
    }
}