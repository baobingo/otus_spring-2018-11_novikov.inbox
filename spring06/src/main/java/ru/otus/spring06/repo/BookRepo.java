package ru.otus.spring06.repo;

import ru.otus.spring06.domain.Author;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Genre;

import java.util.List;

public interface BookRepo {
    int count();
    void insert(Book book);
    void deleteById(long id);
    List<Book> getAll();
    Book getByID(long id);
    List<Book> getByAuthor(Author author);
    List<Book> getByGenre(Genre genre);
}