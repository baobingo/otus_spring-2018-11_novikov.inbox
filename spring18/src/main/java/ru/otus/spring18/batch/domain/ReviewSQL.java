package ru.otus.spring18.batch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReviewSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String body;

    public ReviewSQL(String author, String body) {
        this.author = author;
        this.body = body;
    }

    public ReviewSQL() {
    }

    @Override
    public String toString() {
        return "id: "  + id +
                " author: "+ author +
                " body: " + body + "\n";
    }
}
