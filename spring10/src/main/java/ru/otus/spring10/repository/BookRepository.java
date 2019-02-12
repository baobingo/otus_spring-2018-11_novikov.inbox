package ru.otus.spring10.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring10.domain.Book;


public interface BookRepository extends MongoRepository<Book, Long> {
}
