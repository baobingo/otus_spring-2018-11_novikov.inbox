package ru.otus.spring07.service;

import ru.otus.spring07.domain.Author;
import ru.otus.spring07.domain.Book;
import ru.otus.spring07.domain.Genre;
import ru.otus.spring07.domain.Review;

import java.util.List;

public interface LibraryService {
    long bookCount();
    long authorCount();
    long genreCount();

    List<Book> allBook();
    List<Book> authorsBooks(String authorsName);
    List<Book> genresBooks(String genresTitle);
    List<Review> booksReviews(long booksId);

    void addBook(Book book);
    void addAuthor(Author author);
    void addGenre(Genre genre);
    void addReview(Review review, long bookId);
    void deleteBookById(long id);
    void deleteAuthorById(long id);
    void deleteGenreById(long id);
}
