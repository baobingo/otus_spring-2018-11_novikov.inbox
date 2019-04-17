package ru.otus.spring17.batch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GenreSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public GenreSQL(String name) {
        this.name = name;
    }

    public GenreSQL() {
    }

    public String getName() {
        return name;
    }
}
