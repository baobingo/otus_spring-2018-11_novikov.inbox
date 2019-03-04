package ru.otus.spring11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring11.domain.Book;

public interface BookRepositoryReactive extends ReactiveMongoRepository<Book, Long> {
}
