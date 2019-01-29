package ru.otus.spring08.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
public class Book {

    @Id private int id;

    @Field private String name;

    @Field private Author author;

    @Field private Genre genre;

    @Field private List<Review> reviews = new ArrayList<>();

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public List<Review> getReviews() { return reviews; }

    public void addReview(Review review){
        reviews.add(review);
    }

    @Override
    public String toString() {
        return "[ID] " + getId()
                + " [TITLE] " + getName()
                + " [AUTHOR] " + getAuthor().getName()
                + " [GENRE] " + getGenre().getName() +"\n";
    }
}
