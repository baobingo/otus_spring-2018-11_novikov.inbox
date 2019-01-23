package ru.otus.spring07.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring07.domain.Author;

import java.util.Optional;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
