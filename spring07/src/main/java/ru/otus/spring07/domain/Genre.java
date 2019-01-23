package ru.otus.spring07.domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="seqGenre", initialValue=3, allocationSize=1)
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seqGenre")
    private long id = -1;
    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long id, String name) {
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
