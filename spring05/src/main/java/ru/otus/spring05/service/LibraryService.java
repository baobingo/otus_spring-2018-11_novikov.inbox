package ru.otus.spring05.service;

public interface LibraryService {
    int bookCount();
    int authorCount();
    int genreCount();

    String allBook();
    String authorsBooks(String authorsName);
    String genresBooks(String genresTitle);

    void addBook(String booksName, String authorsName, String genresTitle);
    void addAuthor(String name);
    void addGenre(String name);
    void deleteBookById(String id);
    void deleteAuthorById(String id);
    void deleteGenreById(String id);
}
