package ru.otus.spring06.repo;

import ru.otus.spring06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepo {
    int count();
    void insert(Author author);
    void deleteById(long id);
    List<Author> getAll();
    Optional<Author> getByID(long id);
    Optional<Author> getByName(String name);
    void insertOrId(Author author);
}
