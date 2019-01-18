package ru.otus.spring06.repo;

import ru.otus.spring06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepo {
    int count();
    void insert(Genre genre);
    void deleteById(long id);
    List<Genre> getAll();
    Optional<Genre> getByID(long id);
    Optional<Genre> getByName(String name);
    void insertOrId(Genre genre);
}
