package ru.otus.spring12.repository.security;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring12.domain.security.UserAccount;

public interface UserRepositoryReactive extends ReactiveMongoRepository<UserAccount, Long> {
    Mono<UserAccount> findByUsername(String username);
}
