package ru.otus.spring07.domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="seqAuthor", initialValue=3, allocationSize=1)
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seqAuthor")
    private long id = -1;
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
