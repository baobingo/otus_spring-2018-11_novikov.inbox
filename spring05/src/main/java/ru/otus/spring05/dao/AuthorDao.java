package ru.otus.spring05.dao;

import ru.otus.spring05.domain.Author;

import java.util.List;

public interface AuthorDao {
    int count();
    void insert(Author author);
    void deleteById(int id);
    List<Author> getAll();
    Author getByID(int id);
    Author getByName(String name);
}
