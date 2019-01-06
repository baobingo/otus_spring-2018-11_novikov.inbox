package ru.otus.spring05.service;

import ru.otus.spring05.domain.Book;

import java.util.List;

public interface LibraryService {
    int bookCount();
    int authorCount();
    int genreCount();

    List<Book> allBook();
    List<Book> authorsBooks(String authorsName);
    List<Book> genresBooks(String genresTitle);

    void addBook(String booksName, String authorsName, String genresTitle);
    void addAuthor(String name);
    void addGenre(String name);
    void deleteBookById(long id);
    void deleteAuthorById(long id);
    void deleteGenreById(long id);
}
