package ru.otus.spring05.dao;

import ru.otus.spring05.domain.Author;
import ru.otus.spring05.domain.Book;
import ru.otus.spring05.domain.Genre;

import java.util.List;

public interface BookDao {
    int count();
    void insert(Book book);
    void deleteById(long id);
    List<Book> getAll();
    Book getByID(long id);
    List<Book> getByAuthor(Author author);
    List<Book> getByGenre(Genre genre);
}