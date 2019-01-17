package ru.otus.spring06.service;

import ru.otus.spring06.domain.Author;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Genre;
import ru.otus.spring06.domain.Review;

import java.util.List;

public interface LibraryService {
    int bookCount();
    int authorCount();
    int genreCount();

    List<Book> allBook();
    List<Book> authorsBooks(String authorsName);
    List<Book> genresBooks(String genresTitle);
    List<Review> booksReviews(int booksId);

    void addBook(Book book);
    void addAuthor(Author author);
    void addGenre(Genre genre);
    void addReview(Review review, int bookId);
    void deleteBookById(long id);
    void deleteAuthorById(long id);
    void deleteGenreById(long id);
}
