package ru.otus.spring181.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring181.domain.UserAccount;

@Repository
public interface UserServiceRepo extends MongoRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
}
