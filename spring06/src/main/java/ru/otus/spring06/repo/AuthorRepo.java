package ru.otus.spring06.repo;

import ru.otus.spring06.domain.Author;

import java.util.List;

public interface AuthorRepo {
    int count();
    void insert(Author author);
    void deleteById(long id);
    List<Author> getAll();
    Author getByID(long id);
    Author getByName(String name);
    void insertOrId(Author author);
}
