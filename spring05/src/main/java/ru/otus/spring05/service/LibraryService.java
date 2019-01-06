package ru.otus.spring05.service;

import ru.otus.spring05.domain.Author;
import ru.otus.spring05.domain.Book;
import ru.otus.spring05.domain.Genre;

import java.util.List;

public interface LibraryService {
    int bookCount();
    int authorCount();
    int genreCount();

    List<Book> allBook();
    List<Book> authorsBooks(String authorsName);
    List<Book> genresBooks(String genresTitle);

    void addBook(Book book);
    void addAuthor(Author author);
    void addGenre(Genre genre);
    void deleteBookById(long id);
    void deleteAuthorById(long id);
    void deleteGenreById(long id);
}
