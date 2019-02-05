package ru.otus.spring09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring09.domain.Book;


public interface BookRepository extends MongoRepository<Book, Long> {
}
