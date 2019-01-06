package ru.otus.spring05.service;

import org.springframework.stereotype.Service;
import ru.otus.spring05.dao.AuthorDao;
import ru.otus.spring05.dao.BookDao;
import ru.otus.spring05.dao.GenreDao;
import ru.otus.spring05.domain.Author;
import ru.otus.spring05.domain.Book;
import ru.otus.spring05.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleLibraryService implements LibraryService {

    private AuthorDao authorDao;
    private GenreDao genreDao;
    private BookDao bookDao;

    public SimpleLibraryService(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }

    @Override
    public int bookCount() {
        return bookDao.count();
    }

    @Override
    public int authorCount() {
        return authorDao.count();
    }

    @Override
    public int genreCount() {
        return genreDao.count();
    }

    @Override
    public List<Book> allBook() {
        return bookDao.getAll();
    }

    @Override
    public List<Book> authorsBooks(String authorsName) {
        Author author = authorDao.getByName(authorsName);
        return bookDao.getByAuthor(author);
    }

    @Override
    public List<Book> genresBooks(String genresTitle) {
        Genre genre = genreDao.getByName(genresTitle);
        return bookDao.getByGenre(genre);
    }


    @Override
    public void addBook(Book book) {
        authorDao.insertOrId(book.getAuthor());
        genreDao.insertOrId(book.getGenre());
        bookDao.insert(book);
    }

    @Override
    public void addAuthor(Author author) {
        String name = author.getName();
        Optional.ofNullable(authorDao.getByName(name)).orElseGet(()-> {
            authorDao.insert(new Author(name));
            return authorDao.getByName(name);
        });
    }

    @Override
    public void addGenre(Genre genre) {
        String name = genre.getName();
        Optional.ofNullable(genreDao.getByName(name)).orElseGet(()-> {
            genreDao.insert(new Genre(name));
            return genreDao.getByName(name);
        });
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void deleteAuthorById(long id) {
        authorDao.deleteById(id);
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteById(id);
    }
}
