package ru.otus.spring12.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring12.domain.Book;

public interface BookRepositoryReactive extends ReactiveMongoRepository<Book, Long> {
}
