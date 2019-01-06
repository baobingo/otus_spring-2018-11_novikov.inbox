package ru.otus.spring05.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring05.dao.*;
import ru.otus.spring05.domain.Author;
import ru.otus.spring05.domain.Book;
import ru.otus.spring05.domain.Genre;
import ru.otus.spring05.ui.SimpleHelper;

@ExtendWith({SpringExtension.class})
@JdbcTest
@Import({SimpleAuthorDao.class, SimpleBookDao.class, SimpleGenreDao.class, SimpleLibraryService.class})
@Sql({"/schema-test.sql", "/data-test.sql"})
class SimpleLibraryServiceTest {

    @Autowired
    private LibraryService libraryService;

    @Test
    void bookCount() {
        Assert.assertEquals(2, libraryService.bookCount());
    }

    @Test
    void authorCount() {
        Assert.assertEquals(2, libraryService.authorCount());
    }

    @Test
    void genreCount() {
        Assert.assertEquals(2, libraryService.genreCount());
    }

    @Test
    void allBook() {
        Assert.assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n" +
                "Book #2 ID: 2 Author: Author #2 Genre: Genre #2\n", SimpleHelper.booksToText(libraryService.allBook()));
    }

    @Test
    void authorsBooks() {
        Assert.assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n", SimpleHelper.booksToText(libraryService.authorsBooks("Author #1")));
    }

    @Test
    void genresBooks() {
        Assert.assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n", SimpleHelper.booksToText(libraryService.genresBooks("Genre #1")));
    }

    @Test
    void addBook() {
        libraryService.addBook(new Book("Fairytale", new Author("Author #1"), new Genre("folk")));
        Assert.assertEquals("Book #1 ID: 1 Author: Author #1 Genre: Genre #1\n" +
                "Book #2 ID: 2 Author: Author #2 Genre: Genre #2\n" +
                "Fairytale ID: 3 Author: Author #1 Genre: folk\n" , SimpleHelper.booksToText(libraryService.allBook()));
    }

    @Test
    void addAuthor() {
        libraryService.addAuthor(new Author("Author #3"));
        Assert.assertEquals(3, libraryService.authorCount());
    }

    @Test
    void addGenre() {
        libraryService.addGenre(new Genre("Genre #3"));
        Assert.assertEquals(3, libraryService.genreCount());
    }

    @Test
    void deleteBookById() {
        libraryService.deleteBookById(1);
        Assert.assertEquals("Book #2 ID: 2 Author: Author #2 Genre: Genre #2\n", SimpleHelper.booksToText(libraryService.allBook()));
    }

    @Test
    void deleteAuthorById() {
        libraryService.deleteAuthorById(2);
        Assert.assertEquals(1, libraryService.bookCount());
    }

    @Test
    void deleteGenreById() {
        libraryService.deleteGenreById(1);
        Assert.assertEquals(1, libraryService.bookCount());
    }
}