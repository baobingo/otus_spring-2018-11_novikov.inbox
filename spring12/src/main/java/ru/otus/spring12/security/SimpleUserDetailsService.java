package ru.otus.spring12.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.spring12.domain.security.UserAccount;
import ru.otus.spring12.repository.security.UserRepositoryReactive;

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
