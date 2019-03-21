package ru.otus.spring13.repository.security;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring13.domain.security.UserAccount;

public interface UserRepositoryReactive extends ReactiveMongoRepository<UserAccount, Long> {
    Mono<UserAccount> findByUsername(String username);
}
