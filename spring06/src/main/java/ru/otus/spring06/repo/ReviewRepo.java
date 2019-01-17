package ru.otus.spring06.repo;

import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Review;

import java.util.List;

public interface ReviewRepo {
    int count();
    void insert(Review review);
    void deleteById(long id);
    List<Review> getByBook(Book book);
}
