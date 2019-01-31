package ru.otus.spring08.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Review;

import java.util.List;


public interface BookRepo extends MongoRepository<Book, Long> {

    List<Book> findBooksByAuthorName(String author);
    List<Book> findBooksByGenreName(String genre);
}