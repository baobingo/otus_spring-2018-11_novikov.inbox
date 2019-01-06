package ru.otus.spring05.dao;

import ru.otus.spring05.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();
    void insert(Genre genre);
    void deleteById(long id);
    List<Genre> getAll();
    Genre getByID(long id);
    Genre getByName(String name);
    void insertOrId(Genre genre);
}
