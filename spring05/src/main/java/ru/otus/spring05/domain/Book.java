package ru.otus.spring05.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Book {
    private long id = -1;
    private String name;
    private Author author;
    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setId(long id) {
        this.id = id;
    }
}
