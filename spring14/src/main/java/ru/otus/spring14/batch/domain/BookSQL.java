package ru.otus.spring14.batch.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class BookSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.PERSIST)
    private AuthorSQL author;
    @OneToOne(cascade = CascadeType.PERSIST)
    private GenreSQL genre;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewSQL> reviews;

    public BookSQL(String name, AuthorSQL author, GenreSQL genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public void setReviews(List<ReviewSQL> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewSQL> getReviews() {
        return reviews;
    }

    public BookSQL() {
    }

    @Override
    public String toString() {
        return "id: "  + id +
                " author: "+ author.getName() +
                " genre: " + genre.getName() + "\n";
    }
}
