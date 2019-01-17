package ru.otus.spring06.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seqGenre")
    private long id = -1;
    private String author;
    private String body;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    public Review() {
    }

    public Review(String author, String body) {
        this.author = author;
        this.body = body;
    }

    public Review(String author, String body, Book book) {
        this.author = author;
        this.body = body;
        this.book = book;
    }

    public Review(long id, String author, String body, Book book) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
