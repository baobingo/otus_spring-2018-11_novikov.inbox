package ru.otus.spring07.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring07.domain.Book;
import ru.otus.spring07.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepo extends CrudRepository<Review, Long> {
    List<Review> findByBook(Book book);
}
