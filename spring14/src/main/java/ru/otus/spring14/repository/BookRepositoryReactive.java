package ru.otus.spring14.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;
import ru.otus.spring14.domain.Book;

public interface BookRepositoryReactive extends ReactiveMongoRepository<Book, Long> {
    @Override
    @PreAuthorize("hasPermission(#book, admin)")
    Mono<Void> delete(Book book);
}
