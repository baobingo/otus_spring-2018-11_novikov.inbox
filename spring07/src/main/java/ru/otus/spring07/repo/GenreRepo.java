package ru.otus.spring07.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring07.domain.Genre;

import java.util.Optional;

@Repository
public interface GenreRepo extends CrudRepository<Genre, Long> {
    Optional<Genre> findByName(String Name);
}
