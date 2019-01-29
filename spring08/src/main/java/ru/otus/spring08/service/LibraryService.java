package ru.otus.spring08.service;

import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Review;

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
    void addReview(Review review, long bookId);
    void deleteBookById(long id);
}
