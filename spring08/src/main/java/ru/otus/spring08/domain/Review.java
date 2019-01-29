package ru.otus.spring08.domain;

public class Review {

    private int _id;
    private String author;
    private String body;

    public Review(String author, String body) {
        this.author = author;
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "[ID] " + get_id()
                + " [AUTHOR] " + getAuthor()
                + " [REVIEW] " + getBody() +"\n";
    }
}
