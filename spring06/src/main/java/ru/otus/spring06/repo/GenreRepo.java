package ru.otus.spring06.repo;

import ru.otus.spring06.domain.Genre;

import java.util.List;

public interface GenreRepo {
    int count();
    void insert(Genre genre);
    void deleteById(long id);
    List<Genre> getAll();
    Genre getByID(long id);
    Genre getByName(String name);
    void insertOrId(Genre genre);
}
