package ru.otus.spring13.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.spring13.domain.security.UserAccount;
import ru.otus.spring13.repository.security.UserRepositoryReactive;

@Component
public class SimpleUserDetailsService implements ReactiveUserDetailsService{

    UserRepositoryReactive reactiveUserAccountRepository;

    public SimpleUserDetailsService(UserRepositoryReactive reactiveUserAccountRepository) {
        this.reactiveUserAccountRepository = reactiveUserAccountRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<UserAccount>  userDetailsMono = reactiveUserAccountRepository.findByUsername(username);
        return userDetailsMono.switchIfEmpty(Mono.empty()).cast(UserDetails.class);
    }
}
