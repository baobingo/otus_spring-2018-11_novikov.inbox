package ru.otus.spring07.domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="seqBook", initialValue=3, allocationSize=1)
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seqBook")
    private long id = -1;
    private String name;

    @OneToOne
    private Author author;

    @OneToOne
    private Genre genre;

    public Book() {
    }

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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
